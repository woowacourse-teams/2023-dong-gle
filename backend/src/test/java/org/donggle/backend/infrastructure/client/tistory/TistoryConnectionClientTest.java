package org.donggle.backend.infrastructure.client.tistory;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class TistoryConnectionClientTest {

    private TistoryConnectionClient tistoryConnectionClient;
    private MemberCredentialsRepository memberCredentialsRepository;
    private MemberRepository memberRepository;
    private TistoryApiClient tistoryApiClient;
    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() throws IOException {
        memberCredentialsRepository = mock(MemberCredentialsRepository.class);
        memberRepository = mock(MemberRepository.class);
        tistoryApiClient = mock(TistoryApiClient.class);

        mockWebServer = new MockWebServer();
        mockWebServer.start();

        final WebClient webClient = WebClient.create(mockWebServer.url("/").toString());
        tistoryConnectionClient = new TistoryConnectionClient(
                "clientId",
                "clientSecret",
                memberCredentialsRepository,
                memberRepository,
                tistoryApiClient,
                webClient);
    }

    @AfterEach
    void shutDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("RedirectUri가 정상적으로 만들어지는지 테스트")
    void createAuthorizeRedirectUri() {
        //given
        //when
        //then
        assertThat(tistoryConnectionClient.createAuthorizeRedirectUri("redirect_uri")).isEqualTo("https://www.tistory.com/oauth/authorize?client_id=clientId&redirect_uri=redirect_uri&response_type=code");
    }

    @Test
    @DisplayName("Tistory의 accessToken을 save하는 테스트")
    void saveAccessToken() {
        //given
        final String accessToken = """
                {
                    "access_token": "accessToken"
                }
                """;
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(accessToken).addHeader("Content-Type", "application/json"));

        final Member member = mock(Member.class);
        final MemberCredentials memberCredentials = mock(MemberCredentials.class);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(memberCredentialsRepository.findMemberCredentialsByMember(any(Member.class))).willReturn(Optional.of(memberCredentials));
        given(tistoryApiClient.findDefaultBlogName("accessToken")).willReturn("jeoninpyo726");

        tistoryConnectionClient.saveAccessToken(1L, new OAuthAccessTokenRequest("redirect_uri", "code"));
        then(memberCredentials).should(times(1)).updateTistory("accessToken", "jeoninpyo726");
    }

    @Test
    @DisplayName("tistory의 accessToken을 제거하는 테스트")
    void deleteAccessTokenTest() {
        // Given
        final Long memberId = 1L;
        final Member member = mock(Member.class);
        final MemberCredentials memberCredentials = mock(MemberCredentials.class);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(memberCredentialsRepository.findMemberCredentialsByMember(member)).thenReturn(Optional.of(memberCredentials));

        // When
        tistoryConnectionClient.deleteAccessToken(memberId);

        // Then
        then(memberCredentials).should(times(1)).deleteTistoryConnection();
    }
}