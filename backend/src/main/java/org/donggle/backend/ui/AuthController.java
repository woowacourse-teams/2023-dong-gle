package org.donggle.backend.ui;

import org.donggle.backend.application.service.auth.AuthFacadeService;
import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.donggle.backend.exception.authentication.ExpiredRefreshTokenException;
import org.donggle.backend.ui.response.AccessTokenResponse;
import org.donggle.backend.ui.response.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final int ONE_SECOND = 1000;

    private final int cookieTime;
    private final AuthFacadeService authFacadeService;

    public AuthController(@Value("${security.jwt.token.refresh-token-expire-length}") final int cookieTime,
                          final AuthFacadeService authFacadeService) {
        this.cookieTime = cookieTime / ONE_SECOND;
        this.authFacadeService = authFacadeService;
    }

    @GetMapping("/login/{socialType}/redirect")
    public ResponseEntity<Void> createRedirect(
            @PathVariable final String socialType,
            @RequestParam final String redirect_uri
    ) {
        final String redirectUri = authFacadeService.createAuthorizeRedirectUri(socialType, redirect_uri);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, redirectUri)
                .build();
    }

    @PostMapping("/login/{socialType}")
    public ResponseEntity<AccessTokenResponse> login(
            @PathVariable final String socialType,
            @RequestBody final OAuthAccessTokenRequest oAuthAccessTokenRequest
    ) {
        final TokenResponse response = authFacadeService.login(socialType, oAuthAccessTokenRequest);
        final ResponseCookie cookie = createRefreshTokenCookie(response.refreshToken());

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Set-Cookie", cookie.toString())
                .body(new AccessTokenResponse(response.accessToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue(required = false) final String refreshToken) {
        if (Objects.nonNull(refreshToken)) {
            authFacadeService.logout(refreshToken);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<AccessTokenResponse> reissueAccessToken(@CookieValue(required = false) final String refreshToken) {
        validateRefreshToken(refreshToken);
        final TokenResponse response = authFacadeService.reissueAccessTokenAndRefreshToken(refreshToken);
        final ResponseCookie cookie = createRefreshTokenCookie(response.refreshToken());
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Set-Cookie", cookie.toString())
                .body(new AccessTokenResponse(response.accessToken()));
    }

    private void validateRefreshToken(final String refreshToken) {
        if (Objects.isNull(refreshToken)) {
            throw new ExpiredRefreshTokenException();
        }
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
