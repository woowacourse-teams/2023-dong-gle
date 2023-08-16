package org.donggle.backend.ui;

import org.donggle.backend.application.service.AuthService;
import org.donggle.backend.auth.support.AuthenticationPrincipal;
import org.donggle.backend.ui.response.AccessTokenResponse;
import org.donggle.backend.ui.response.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final int cookieTime;

    private final AuthService authService;

    public AuthController(@Value("${security.jwt.token.refresh-token-expire-length}") final int cookieTime,
                          final AuthService authService
    ) {
        this.cookieTime = cookieTime;
        this.authService = authService;
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal final Long memberId) {
        authService.logout(memberId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<AccessTokenResponse> reissueAccessToken(@AuthenticationPrincipal final Long memberId) {
        final TokenResponse response = authService.reissueAccessTokenAndRefreshToken(memberId);

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
