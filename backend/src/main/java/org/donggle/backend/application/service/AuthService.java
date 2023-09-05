package org.donggle.backend.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.TokenRepository;
import org.donggle.backend.application.service.oauth.kakao.OauthClients;
import org.donggle.backend.application.service.oauth.kakao.SocialType;
import org.donggle.backend.application.service.oauth.kakao.dto.UserInfo;
import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.donggle.backend.auth.JwtTokenProvider;
import org.donggle.backend.auth.RefreshToken;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.donggle.backend.ui.response.TokenResponse;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final OauthClients oauthClients;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    private final MemberCredentialsRepository memberCredentialsRepository;
    private final CategoryRepository categoryRepository;

    public String createAuthorizeRedirectUri(final String socialType, final String redirect_uri) {
        return oauthClients.redirectUri(SocialType.from(socialType), redirect_uri);
    }

    public TokenResponse login(final String socialType, final OAuthAccessTokenRequest request) {
        final UserInfo userInfo = oauthClients.requestUserInfo(SocialType.from(socialType), request.code(), request.redirect_uri());
        final Member loginMember = memberRepository.findByKakaoId(userInfo.socialId())
                .orElseGet(() -> initializeMember(userInfo));
        return createTokens(loginMember);
    }

    public void logout(final Long memberId) {
        tokenRepository.deleteByMemberId(memberId);
    }

    private Member initializeMember(final UserInfo userInfo) {
        final Member newMember = memberRepository.save(userInfo.toMember());
        final Category basicCategory = Category.basic(newMember);
        final MemberCredentials basic = MemberCredentials.basic(newMember);

        categoryRepository.save(basicCategory);
        memberCredentialsRepository.save(basic);
        return newMember;
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
