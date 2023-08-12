package org.donggle.backend.application.service.oauth.tistory;

import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TistoryOAuthService {
    private static final String AUTHORIZE_URL = "https://www.tistory.com/oauth/authorize";
    private static final String TOKEN_URL = "https://www.tistory.com/oauth/access_token";
    public static final String CLIENT_ID = "client_id";
    public static final String REDIRECT_URI = "redirect_uri";

    private final String clientId;
    private final String clientSecret;
    private final WebClient webClient;

    public TistoryOAuthService(@Value("${tistory_client_id}") final String clientId,
                               @Value("${tistory_client_secret}") final String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.webClient = WebClient.create();
    }

    public String createAuthorizeRedirectUri(final String redirectUri) {
        return UriComponentsBuilder.fromUriString(AUTHORIZE_URL)
                .queryParam(CLIENT_ID, clientId)
                .queryParam(REDIRECT_URI, redirectUri)
                .queryParam("response_type", "code")
                .build()
                .toUriString();
    }

    public String getAccessToken(final OAuthAccessTokenRequest oAuthAccessTokenRequest) {
        final String redirectUri = oAuthAccessTokenRequest.redirect_uri();
        final String code = oAuthAccessTokenRequest.code();
        final String tokenUri = createTokenUri(redirectUri, code);

        return webClient.get()
                .uri(tokenUri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String createTokenUri(final String redirectUri, final String code) {
        return UriComponentsBuilder.fromUriString(TOKEN_URL)
                .queryParam(CLIENT_ID, clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam(REDIRECT_URI, redirectUri)
                .queryParam("code", code)
                .queryParam("grant_type", "authorization_code")
                .build()
                .toUriString();
    }
}
