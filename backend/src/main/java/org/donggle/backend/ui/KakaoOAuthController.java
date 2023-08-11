package org.donggle.backend.ui;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.AuthService;
import org.donggle.backend.application.service.oauth.kakao.KakaoOAuthService;
import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.donggle.backend.auth.support.AuthenticationPrincipal;
import org.donggle.backend.ui.response.AccessTokenResponse;
import org.donggle.backend.ui.response.TokenResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaoOAuthController {
    private static final int ONE_DAYS = 24 * 60 * 60;

    private final KakaoOAuthService kakaoOAuthService;
    private final AuthService authService;

    @GetMapping("/oauth/login/kakao")
    public ResponseEntity<Void> oauthRedirectKakao(@RequestParam final String redirect_uri) {
        final String redirectUri = kakaoOAuthService.createAuthorizeRedirectUri(redirect_uri);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, redirectUri)
                .build();
    }

    @PostMapping("/oauth/login/kakao")
    public ResponseEntity<AccessTokenResponse> oauthRedirectKakao(@RequestBody final OAuthAccessTokenRequest oAuthAccessTokenRequest, final HttpServletResponse httpServletResponse) {
        final TokenResponse response = kakaoOAuthService.login(oAuthAccessTokenRequest);

        final ResponseCookie cookie = createRefreshTokenCookie(response.refreshToken());
        httpServletResponse.setHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok(new AccessTokenResponse(response.accessToken()));
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<AccessTokenResponse> reissueAccessToken(@AuthenticationPrincipal final Long memberId) {
        final AccessTokenResponse response = authService.reissueAccessToken(memberId);
        return ResponseEntity.ok(response);
    }

    private ResponseCookie createRefreshTokenCookie(final String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .maxAge(KakaoOAuthController.ONE_DAYS)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();
    }
}
