package org.donggle.backend.application.service.oauth.tistory;

import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.donggle.backend.application.service.vendor.tistory.TistoryApiService;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
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
    private final MemberCredentialsRepository memberCredentialsRepository;
    private final MemberRepository memberRepository;
    private final TistoryApiService tistoryApiService;

    public TistoryOAuthService(@Value("${tistory_client_id}") final String clientId,
                               @Value("${tistory_client_secret}") final String clientSecret,
                               final MemberCredentialsRepository memberCredentialsRepository,
                               final MemberRepository memberRepository,
                               final TistoryApiService tistoryApiService) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.memberCredentialsRepository = memberCredentialsRepository;
        this.memberRepository = memberRepository;
        this.tistoryApiService = tistoryApiService;
        this.webClient = WebClient.create();
    }

    public String createAuthorizeRedirectUri(final Long memberId, final String redirectUri) {
        final boolean memberExists = memberRepository.existsById(memberId);
        if (memberExists) {
            return UriComponentsBuilder.fromUriString(AUTHORIZE_URL)
                    .queryParam(CLIENT_ID, clientId)
                    .queryParam(REDIRECT_URI, redirectUri)
                    .queryParam("response_type", "code")
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
                .map(memberCredentials -> memberCredentials.updateTistoryToken(accessToken))
                .orElseGet(() -> creatMemberCredentials(member, accessToken));

        memberCredentialsRepository.save(findMemberCredentials);
    }

    private MemberCredentials creatMemberCredentials(final Member member, final String accessToken) {
        final String tistoryBlogName = tistoryApiService.getDefaultTistoryBlogName(accessToken);
        return MemberCredentials.createByTistoryToken(member, accessToken, tistoryBlogName);
    }

    private String getAccessToken(final String code, final String redirectUri) {
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
