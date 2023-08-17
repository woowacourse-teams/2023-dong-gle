package org.donggle.backend.ui;

import org.donggle.backend.application.service.oauth.kakao.KakaoOAuthService;
import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.donggle.backend.ui.response.AccessTokenResponse;
import org.donggle.backend.ui.response.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
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
public class KakaoOAuthController {
    private static final int ONE_SECOND = 1000;

    private final int cookieTime;

    private final KakaoOAuthService kakaoOAuthService;

    public KakaoOAuthController(@Value("${security.jwt.token.refresh-token-expire-length}") final int cookieTime,
                                final KakaoOAuthService kakaoOAuthService
    ) {
        this.cookieTime = cookieTime / ONE_SECOND;
        this.kakaoOAuthService = kakaoOAuthService;
    }

    @GetMapping("/oauth/login/kakao")
    public ResponseEntity<Void> oauthRedirectKakao(@RequestParam final String redirect_uri) {
        final String redirectUri = kakaoOAuthService.createAuthorizeRedirectUri(redirect_uri);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, redirectUri)
                .build();
    }

    @PostMapping("/oauth/login/kakao")
    public ResponseEntity<AccessTokenResponse> oauthRedirectKakao(@RequestBody final OAuthAccessTokenRequest oAuthAccessTokenRequest) {
        final TokenResponse response = kakaoOAuthService.login(oAuthAccessTokenRequest);

        final ResponseCookie cookie = createRefreshTokenCookie(response.refreshToken());

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Set-Cookie", cookie.toString())
                .body(new AccessTokenResponse(response.accessToken()));
    }

    private ResponseCookie createRefreshTokenCookie(final String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .maxAge(cookieTime)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();
    }
}
