package org.donggle.backend.application.service.oauth.tistory;

import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TistoryOAuthService {
    private static final String AUTHORIZE_URL = "https://www.tistory.com/oauth/authorize";
    private static final String TOKEN_URL = "https://www.tistory.com/oauth/access_token";

    private final String clientId;
    private final String clientSecret;
    private final WebClient webClient;

    public TistoryOAuthService(@Value("${tistory_client_id}") final String clientId, @Value("${tistory_client_secret}") final String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.webClient = WebClient.create();
    }

    public String createRedirectUri(final String redirectUri) {
        return AUTHORIZE_URL + "?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&response_type=code";
    }

    public String getAccessToken(final OAuthAccessTokenRequest oAuthAccessTokenRequest) {
        final String redirectUri = oAuthAccessTokenRequest.redirectUri();
        final String code = oAuthAccessTokenRequest.code();
        return webClient.get()
                .uri(TOKEN_URL + "?client_id=" + clientId + "&client_secret=" + clientSecret + "&redirect_uri=" + redirectUri + "&code=" + code + "&grant_type=authorization_code")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
