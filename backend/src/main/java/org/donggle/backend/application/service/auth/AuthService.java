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
import org.donggle.backend.exception.business.DuplicatedMemberException;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.donggle.backend.infrastructure.oauth.kakao.dto.response.SocialUserInfo;
import org.donggle.backend.ui.response.TokenResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    private final MemberCredentialsRepository memberCredentialsRepository;
    private final CategoryRepository categoryRepository;


    public TokenResponse login(final SocialUserInfo socialUserInfo) {
        return memberRepository.findBySocialId(socialUserInfo.socialId())
                .map(memberInfo -> new Member(memberInfo.id(), new MemberName(socialUserInfo.nickname()), memberInfo.socialId()))
                .map(this::createTokens)
                .orElse(createTokens(initializeMember(socialUserInfo)));
    }

    public void logout(final Long memberId) {
        tokenRepository.deleteByMemberId(memberId);
    }

    private Member initializeMember(final SocialUserInfo socialUserInfo) {
        Member member = socialUserInfo.toMember();
        final Category basicCategory = Category.basic(member);
        final MemberCredentials basic = MemberCredentials.basic(member);
        try {
            member = memberRepository.save(member);
            categoryRepository.save(basicCategory);
            memberCredentialsRepository.save(basic);
        } catch (final DuplicateKeyException e) {
            throw new DuplicatedMemberException(socialUserInfo.socialType().name());
        }
        return member;
    }

    public TokenResponse reissueAccessTokenAndRefreshToken(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        return createTokens(member);
    }

    private TokenResponse createTokens(final Member loginMember) {
        final String accessToken = jwtTokenProvider.createAccessToken(loginMember.getId());
        final String refreshToken = jwtTokenProvider.createRefreshToken(loginMember.getId());

        synchronizeRefreshToken(loginMember, refreshToken);

        return new TokenResponse(accessToken, refreshToken);
    }

    private void synchronizeRefreshToken(final Member member, final String refreshToken) {
        tokenRepository.findByMemberId(member.getId())
                .ifPresentOrElse(
                        token -> token.update(refreshToken),
                        () -> tokenRepository.save(new RefreshToken(refreshToken, member))
                );
    }

}
