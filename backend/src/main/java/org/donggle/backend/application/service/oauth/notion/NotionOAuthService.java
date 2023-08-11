package org.donggle.backend.application.service.oauth.notion;

import org.donggle.backend.application.service.oauth.notion.dto.NotionTokenRequest;
import org.donggle.backend.application.service.oauth.notion.dto.NotionTokenResponse;
import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class NotionOAuthService {
    public static final String AUTHORIZE_URL = "https://api.notion.com/v1/oauth/authorize";
    public static final String TOKEN_URL = "https://api.notion.com/v1/oauth/token";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String RESPONSE_TYPE = "code";
    private static final String OWNER = "user";

    private final String clientId;
    private final String clientSecret;
    private final WebClient webClient;

    public NotionOAuthService(@Value("${notion_client_id}") final String clientId,
                              @Value("${notion_client_secret}") final String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.webClient = WebClient.create();
    }

    public String createRedirectUri(final String redirectUri) {
        return UriComponentsBuilder.fromUriString(AUTHORIZE_URL)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", RESPONSE_TYPE)
                .queryParam("owner", OWNER)
                .build()
                .toUriString();
    }

    public void getAccessToken(final OAuthAccessTokenRequest oAuthAccessTokenRequest) {
        final String redirectUri = oAuthAccessTokenRequest.redirectUri();
        final String code = oAuthAccessTokenRequest.code();

        NotionTokenResponse response = webClient.post()
                .uri(TOKEN_URL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Encode(clientId + ":" + clientSecret))
                .bodyValue(new NotionTokenRequest(GRANT_TYPE, code, redirectUri))
                .retrieve()
                .bodyToMono(NotionTokenResponse.class)
                .block();
    }

    private String base64Encode(String value) {
        return java.util.Base64.getEncoder().encodeToString(value.getBytes());
    }
}