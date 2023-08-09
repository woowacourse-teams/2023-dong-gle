package org.donggle.backend.application.service.oauth.kakao;

import org.donggle.backend.application.service.oauth.kakao.dto.KakaoProfileResponse;
import org.donggle.backend.application.service.oauth.kakao.dto.KakaoTokenResponse;
import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class KakaoOAuthService {
    public static final String GRANT_TYPE = "authorization_code";
    private static final String RESPONSE_TYPE = "code";
    public static final String PROFILE_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String AUTHORIZE_URL = "https://kauth.kakao.com/oauth/authorize";
    private static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";

    private final String clientId;
    private final String clientSecret;
    private final WebClient webClient;

    public KakaoOAuthService(@Value("${kakao_client_id}") final String clientId, @Value("${kakao_client_secret}") final String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        webClient = WebClient.create();
    }

    public String createRedirectUri(final String redirectUri) {
        return AUTHORIZE_URL + "?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&response_type=" + RESPONSE_TYPE;
    }

    public String requestAccessToken(final OAuthAccessTokenRequest oAuthAccessTokenRequest) {
        final BodyInserters.FormInserter<String> bodyForm = BodyInserters.fromFormData("grant_type", GRANT_TYPE)
                .with("client_id", clientId)
                .with("redirect_uri", oAuthAccessTokenRequest.redirectUri())
                .with("code", oAuthAccessTokenRequest.code())
                .with("client_secret", clientSecret);

        final KakaoTokenResponse kakaoTokenResponse = webClient.post()
                .uri(TOKEN_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(bodyForm)
                .retrieve()
                .bodyToMono(KakaoTokenResponse.class)
                .block();
        return Objects.requireNonNull(kakaoTokenResponse).access_token();
    }

    public KakaoProfileResponse requestKakaoProfile(final String accessToken) {
        return webClient.get()
                .uri(PROFILE_URL)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoProfileResponse.class)
                .block();
    }
}
