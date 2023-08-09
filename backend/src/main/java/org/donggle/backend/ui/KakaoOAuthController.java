package org.donggle.backend.ui;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.oauth.kakao.KakaoOAuthService;
import org.donggle.backend.application.service.oauth.kakao.dto.KakaoProfileResponse;
import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class KakaoOAuthController {
    private final KakaoOAuthService kakaoOAuthService;

    @GetMapping("/oauth/login/kakao")
    public ResponseEntity<Void> oauthRedirectKakao(@RequestParam final String redirect_uri) {
        final HttpHeaders headers = new HttpHeaders();
        final String redirectUri = kakaoOAuthService.createRedirectUri(redirect_uri);
        headers.setLocation(URI.create(redirectUri));
        return new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);
    }

    @PostMapping("/oauth/login/kakao")
    public ResponseEntity<Void> oauthRedirectKakao(@RequestBody final OAuthAccessTokenRequest oAuthAccessTokenRequest) {
        final String accessToken = kakaoOAuthService.requestAccessToken(oAuthAccessTokenRequest);
        final KakaoProfileResponse kakaoProfileResponse = kakaoOAuthService.requestKakaoProfile(accessToken);
        return ResponseEntity.ok().build();
    }
}
