package org.donggle.backend.application.service.auth;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.donggle.backend.domain.auth.JwtTokenProvider;
import org.donggle.backend.domain.oauth.SocialType;
import org.donggle.backend.infrastructure.oauth.kakao.dto.response.UserInfo;
import org.donggle.backend.ui.response.TokenResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthFacadeService {
    private final LoginClients oauthClients;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public String createAuthorizeRedirectUri(final String socialType, final String redirect_uri) {
        return oauthClients.redirectUri(SocialType.from(socialType), redirect_uri);
    }

    public TokenResponse login(final String socialType, final OAuthAccessTokenRequest request) {
        final SocialType type = SocialType.from(socialType);
        final UserInfo socialUserInfo = oauthClients.findUserInfo(
                type,
                request.code(),
                request.redirect_uri());
        return authService.login(socialUserInfo, type);
    }

    public void logout(final String refreshToken) {
        final Long memberId = jwtTokenProvider.getPayload(refreshToken);
        authService.logout(memberId);
    }

    public TokenResponse reissueAccessTokenAndRefreshToken(final String refreshToken) {
        return authService.reissueAccessTokenAndRefreshToken(refreshToken);
    }
}
