package org.donggle.backend.application.service.oauth.notion;

import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.service.oauth.notion.dto.NotionTokenRequest;
import org.donggle.backend.application.service.oauth.notion.dto.NotionTokenResponse;
import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Service
public class NotionConnectionService {
    public static final String AUTHORIZE_URL = "https://api.notion.com/v1/oauth/authorize";
    public static final String TOKEN_URL = "https://api.notion.com/v1/oauth/token";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String RESPONSE_TYPE = "code";
    private static final String OWNER = "user";

    private final String clientId;
    private final String clientSecret;
    private final WebClient webClient;
    private final MemberRepository memberRepository;
    private final MemberCredentialsRepository memberCredentialsRepository;

    public NotionConnectionService(@Value("${notion_client_id}") final String clientId,
                                   @Value("${notion_client_secret}") final String clientSecret, final MemberRepository memberRepository, final MemberCredentialsRepository memberCredentialsRepository) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.memberRepository = memberRepository;
        this.memberCredentialsRepository = memberCredentialsRepository;
        this.webClient = WebClient.create();
    }

    public String createRedirectUri(final Long memberId, final String redirectUri) {
        final boolean memberExists = memberRepository.existsById(memberId);
        if (memberExists) {
            return UriComponentsBuilder.fromUriString(AUTHORIZE_URL)
                    .queryParam("client_id", clientId)
                    .queryParam("redirect_uri", redirectUri)
                    .queryParam("response_type", RESPONSE_TYPE)
                    .queryParam("owner", OWNER)
                    .build()
                    .toUriString();
        }
        throw new MemberNotFoundException(memberId);
    }

    public void saveAccessToken(final Long memberId, final OAuthAccessTokenRequest oAuthAccessTokenRequest) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        final String accessToken = getAccessToken(oAuthAccessTokenRequest.code(), oAuthAccessTokenRequest.redirect_uri());

        final MemberCredentials findMemberCredentials = memberCredentialsRepository.findMemberCredentialsByMember(member)
                .map(memberCredentials -> memberCredentials.updateNotionToken(accessToken))
                .orElseGet(() -> creatMemberCredentials(member, accessToken));

        memberCredentialsRepository.save(findMemberCredentials);
    }

    private MemberCredentials creatMemberCredentials(final Member member, final String accessToken) {
        return MemberCredentials.createByNotionToken(member, accessToken);
    }

    private String getAccessToken(final String code, final String redirectUri) {
        return Objects.requireNonNull(webClient.post()
                .uri(TOKEN_URL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Encode(clientId + ":" + clientSecret))
                .bodyValue(new NotionTokenRequest(GRANT_TYPE, code, redirectUri))
                .retrieve()
                .bodyToMono(NotionTokenResponse.class)
                .block()).access_token();
    }

    private String base64Encode(final String value) {
        return java.util.Base64.getEncoder().encodeToString(value.getBytes());
    }
}
