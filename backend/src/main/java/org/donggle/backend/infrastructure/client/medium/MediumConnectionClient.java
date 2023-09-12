package org.donggle.backend.infrastructure.client.medium;

import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.service.request.TokenAddRequest;
import org.donggle.backend.infrastructure.client.exception.ClientException;
import org.donggle.backend.infrastructure.client.exception.ClientInvalidTokenException;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

@Service
@Transactional
public class MediumConnectionClient {
    private static final String MEDIUM_PROFILE_URL = "https://api.medium.com/v1/me";
    private static final String PLATFORM_NAME = "Medium";
    private final MemberRepository memberRepository;
    private final MemberCredentialsRepository memberCredentialsRepository;
    private final WebClient webClient;

    public MediumConnectionClient(
            final MemberRepository memberRepository,
            final MemberCredentialsRepository memberCredentialsRepository) {
        this.memberRepository = memberRepository;
        this.memberCredentialsRepository = memberCredentialsRepository;
        this.webClient = WebClient.create();
    }

    public void saveAccessToken(final Long memberId, final TokenAddRequest addTokenRequest) {
        checkConnection(addTokenRequest.token());

        final Member member = findMember(memberId);
        final MemberCredentials memberCredentials = findMemberCredentials(member);

        memberCredentials.updateMediumToken(addTokenRequest.token());
    }

    private void checkConnection(final String token) {
        webClient.get()
                .uri(MEDIUM_PROFILE_URL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handle4xxException(clientResponse.statusCode().value()))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> ClientException.handle5xxException(PLATFORM_NAME))
                .bodyToMono(String.class)
                .block();
    }

    public void deleteAccessToken(final Long memberId) {
        final Member member = findMember(memberId);
        final MemberCredentials memberCredentials = findMemberCredentials(member);

        memberCredentials.deleteMediumConnection();
    }

    private MemberCredentials findMemberCredentials(final Member member) {
        return memberCredentialsRepository.findMemberCredentialsByMember(member)
                .orElseThrow(NoSuchElementException::new);
    }

    private Member findMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    private Mono<? extends Throwable> handle4xxException(final int code) {
        return Mono.error(new ClientInvalidTokenException(code, PLATFORM_NAME));
    }
}
