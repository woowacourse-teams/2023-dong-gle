package org.donggle.backend.application.service.auth;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.TokenRepository;
import org.donggle.backend.domain.auth.JwtTokenProvider;
import org.donggle.backend.domain.auth.RefreshToken;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.domain.member.MemberName;
import org.donggle.backend.domain.oauth.SocialType;
import org.donggle.backend.exception.authentication.ExpiredRefreshTokenException;
import org.donggle.backend.exception.authentication.InvalidRefreshTokenException;
import org.donggle.backend.exception.business.DuplicatedMemberException;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.donggle.backend.exception.notfound.RefreshTokenNotFoundException;
import org.donggle.backend.infrastructure.oauth.kakao.dto.response.UserInfo;
import org.donggle.backend.ui.response.TokenResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    private final MemberCredentialsRepository memberCredentialsRepository;
    private final CategoryRepository categoryRepository;

    public TokenResponse login(final UserInfo userInfo, final SocialType type) {
        final Member member = memberRepository.findBySocialIdAndSocialType(userInfo.socialId(), type)
                .map(info -> Member.of(info.id(), new MemberName(userInfo.nickname()), info.socialId(), userInfo.socialType()))
                .orElseGet(() -> initializeMember(userInfo));
        final Optional<RefreshToken> refreshTokenOptional = tokenRepository.findByMemberId(member.getId());
        final String newAccessToken = jwtTokenProvider.createAccessToken(member.getId());
        final String newRefreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        refreshTokenOptional.ifPresentOrElse(
                refreshToken -> refreshToken.update(newRefreshToken),
                () -> tokenRepository.save(new RefreshToken(newRefreshToken, member))
        );
        return new TokenResponse(newAccessToken, newRefreshToken);
    }

    private Member initializeMember(final UserInfo userInfo) {
        Member member = userInfo.toMember();
        final Category basicCategory = Category.basic(member);
        final MemberCredentials basic = MemberCredentials.basic(member);
        try {
            memberRepository.save(member);
            categoryRepository.save(basicCategory);
            memberCredentialsRepository.save(basic);
        } catch (final DuplicateKeyException e) {
            throw new DuplicatedMemberException(userInfo.socialType().name());
        }
        return member;
    }

    public void logout(final Long memberId) {
        tokenRepository.deleteByMemberId(memberId);
    }

    public TokenResponse reissueAccessTokenAndRefreshToken(final String refreshToken) {
        final Long memberId = jwtTokenProvider.getPayload(refreshToken);
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException(null);
        }
        final RefreshToken findRefreshToken = tokenRepository.findByMemberId(memberId)
                .orElseThrow(RefreshTokenNotFoundException::new);

        validateRefreshToken(findRefreshToken, refreshToken);

        final String newAccessToken = jwtTokenProvider.createAccessToken(memberId);
        final String newRefreshToken = jwtTokenProvider.createRefreshToken(memberId);
        findRefreshToken.update(newRefreshToken);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }

    private void validateRefreshToken(final RefreshToken refreshToken, final String refreshTokenValue) {
        if (refreshToken.isDifferentFrom(refreshTokenValue)) {
            throw new InvalidRefreshTokenException();
        }
        if (jwtTokenProvider.inValidTokenUsage(refreshTokenValue)) {
            throw new ExpiredRefreshTokenException();
        }
    }
}
