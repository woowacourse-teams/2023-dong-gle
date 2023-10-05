package org.donggle.backend.infrastructure.client.notion;

import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.donggle.backend.infrastructure.client.exception.ClientException;
import org.donggle.backend.infrastructure.client.notion.dto.request.NotionTokenRequest;
import org.donggle.backend.infrastructure.client.notion.dto.response.NotionTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
public class NotionConnectionClient {
    public static final String AUTHORIZE_URL = "https://api.notion.com/v1/oauth/authorize";
    public static final String TOKEN_URL = "https://api.notion.com/v1/oauth/token";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String RESPONSE_TYPE = "code";
    private static final String OWNER = "user";
    private static final String PLATFORM_NAME = "Notion";

    private final String clientId;
    private final String clientSecret;
    private final WebClient webClient;
    private final MemberRepository memberRepository;
    private final MemberCredentialsRepository memberCredentialsRepository;

    public NotionConnectionClient(@Value("${notion_client_id}") final String clientId,
                                  @Value("${notion_client_secret}") final String clientSecret, final MemberRepository memberRepository, final MemberCredentialsRepository memberCredentialsRepository) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.memberRepository = memberRepository;
        this.memberCredentialsRepository = memberCredentialsRepository;
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

    public void saveAccessToken(final Long memberId, final OAuthAccessTokenRequest oAuthAccessTokenRequest) {
        final Member member = findMember(memberId);
        final MemberCredentials memberCredentials = findMemberCredentials(member);
        final String accessToken = getAccessToken(oAuthAccessTokenRequest.code(), oAuthAccessTokenRequest.redirect_uri());
        memberCredentials.updateNotionToken(accessToken);
    }

    private String getAccessToken(final String code, final String redirectUri) {
        return Objects.requireNonNull(webClient.post()
                        .uri(TOKEN_URL)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Encode(clientId + ":" + clientSecret))
                        .bodyValue(new NotionTokenRequest(GRANT_TYPE, code, redirectUri))
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> ClientException.handle4xxException(clientResponse.statusCode().value(), PLATFORM_NAME))
                        .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> ClientException.handle5xxException(PLATFORM_NAME)))
                .bodyToMono(NotionTokenResponse.class)
                .block().access_token();
    }

    private String base64Encode(final String value) {
        return java.util.Base64.getEncoder().encodeToString(value.getBytes());
    }

    public void deleteAccessToken(final Long memberId) {
        final Member member = findMember(memberId);
        final MemberCredentials memberCredentials = findMemberCredentials(member);

        memberCredentials.deleteNotionConnection();
    }

    private Member findMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    private MemberCredentials findMemberCredentials(final Member member) {
        return memberCredentialsRepository.findByMember(member)
                .orElseThrow(NoSuchElementException::new);
    }
}
