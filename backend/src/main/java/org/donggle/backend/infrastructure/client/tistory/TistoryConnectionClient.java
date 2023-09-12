package org.donggle.backend.infrastructure.client.tistory;

import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.infrastructure.client.tistory.dto.response.TistoryAccessTokenResponse;
import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.donggle.backend.infrastructure.client.exception.ClientException;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.NoSuchElementException;

@Service
@Transactional
public class TistoryConnectionClient {
    private static final String AUTHORIZE_URL = "https://www.tistory.com/oauth/authorize";
    private static final String TOKEN_URL = "https://www.tistory.com/oauth/access_token";
    private static final String CLIENT_ID = "client_id";
    private static final String PLATFORM_NAME = "Tistory";

    private final String clientId;
    private final String clientSecret;
    private final WebClient webClient;
    private final MemberCredentialsRepository memberCredentialsRepository;
    private final MemberRepository memberRepository;
    private final TistoryApiClient tistoryApiService;

    public TistoryConnectionClient(@Value("${tistory_client_id}") final String clientId,
                                   @Value("${tistory_client_secret}") final String clientSecret,
                                   final MemberCredentialsRepository memberCredentialsRepository,
                                   final MemberRepository memberRepository,
                                   final TistoryApiClient tistoryApiService) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.memberCredentialsRepository = memberCredentialsRepository;
        this.memberRepository = memberRepository;
        this.tistoryApiService = tistoryApiService;
        this.webClient = WebClient.create();
    }

    public String createAuthorizeRedirectUri(final String redirectUri) {
        return UriComponentsBuilder.fromUriString(AUTHORIZE_URL)
                .queryParam(CLIENT_ID, clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .build()
                .toUriString();
    }

    public void saveAccessToken(final Long memberId, final OAuthAccessTokenRequest oAuthAccessTokenRequest) {
        final Member member = findMember(memberId);
        final MemberCredentials memberCredentials = findMemberCredentials(member);

        final String accessToken = getAccessToken(oAuthAccessTokenRequest.code(), oAuthAccessTokenRequest.redirect_uri());
        final String tistoryBlogName = tistoryApiService.getDefaultTistoryBlogName(accessToken);

        memberCredentials.updateTistory(accessToken, tistoryBlogName);
    }

    public String getAccessToken(final String code, final String redirectUri) {
        final String tokenUri = createTokenUri(redirectUri, code);

        return webClient.get()
                .uri(tokenUri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> ClientException.handle4xxException(clientResponse.statusCode().value(), PLATFORM_NAME))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> ClientException.handle5xxException(PLATFORM_NAME))
                .bodyToMono(TistoryAccessTokenResponse.class)
                .block().access_token();
    }

    public String createTokenUri(final String redirectUri, final String code) {
        return UriComponentsBuilder.fromUriString(TOKEN_URL)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("code", code)
                .queryParam("grant_type", "authorization_code")
                .build()
                .toUriString();
    }

    public void deleteAccessToken(final Long memberId) {
        final Member member = findMember(memberId);
        final MemberCredentials memberCredentials = findMemberCredentials(member);

        memberCredentials.deleteTistoryConnection();
    }

    private Member findMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    private MemberCredentials findMemberCredentials(final Member member) {
        return memberCredentialsRepository.findMemberCredentialsByMember(member)
                .orElseThrow(NoSuchElementException::new);
    }
}
