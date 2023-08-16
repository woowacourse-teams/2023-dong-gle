package org.donggle.backend.application.service.oauth.kakao;

import jakarta.persistence.FetchType;
import org.donggle.backend.application.service.AuthService;
import org.donggle.backend.application.service.oauth.kakao.dto.KakaoProfileResponse;
import org.donggle.backend.application.service.oauth.kakao.dto.KakaoTokenResponse;
import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.donggle.backend.application.service.vendor.exception.VendorApiException;
import org.donggle.backend.ui.response.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class KakaoOAuthService {
    private static final String GRANT_TYPE = "authorization_code";
    private static final String RESPONSE_TYPE = "code";
    private static final String PROFILE_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String AUTHORIZE_URL = "https://kauth.kakao.com/oauth/authorize";
    private static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String PLATFORM_NAME = "Kakao";

    private final String clientId;
    private final String clientSecret;
    private final WebClient webClient;
    private final AuthService authService;

    public KakaoOAuthService(@Value("${kakao_client_id}") final String clientId,
                             @Value("${kakao_client_secret}") final String clientSecret,
                             final AuthService authService) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authService = authService;
        webClient = WebClient.create();
    }

    public String createAuthorizeRedirectUri(final String redirectUri) {
        return UriComponentsBuilder.fromUriString(AUTHORIZE_URL)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", RESPONSE_TYPE)
                .build()
                .toUriString();
    }

    public TokenResponse login(final OAuthAccessTokenRequest oAuthAccessTokenRequest) {
        final String accessToken = requestAccessToken(oAuthAccessTokenRequest);
        final KakaoProfileResponse kakaoProfileResponse = requestKakaoProfile(accessToken);
        return authService.loginByKakao(kakaoProfileResponse);
    }

    private String requestAccessToken(final OAuthAccessTokenRequest oAuthAccessTokenRequest) {
        final BodyInserters.FormInserter<String> bodyForm = BodyInserters.fromFormData("grant_type", GRANT_TYPE)
                .with("client_id", clientId)
                .with("redirect_uri", oAuthAccessTokenRequest.redirect_uri())
                .with("code", oAuthAccessTokenRequest.code())
                .with("client_secret", clientSecret);

        final KakaoTokenResponse kakaoTokenResponse = webClient.post()
                .uri(TOKEN_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(bodyForm)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> VendorApiException.handle4xxException(clientResponse.statusCode().value(),PLATFORM_NAME))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException(clientResponse.toString())))
                .bodyToMono(KakaoTokenResponse.class)
                .block();
        return Objects.requireNonNull(kakaoTokenResponse).access_token();
    }

    private KakaoProfileResponse requestKakaoProfile(final String accessToken) {
        return webClient.get()
                .uri(PROFILE_URL)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoProfileResponse.class)
                .block();
    }
}
