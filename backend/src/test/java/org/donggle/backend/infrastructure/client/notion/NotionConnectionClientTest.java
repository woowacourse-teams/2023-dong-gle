package org.donggle.backend.infrastructure.client.notion;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.assertj.core.api.Assertions;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class NotionConnectionClientTest {
    private MemberRepository memberRepository;
    private MemberCredentialsRepository memberCredentialsRepository;
    private NotionConnectionClient notionConnectionClient;
    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        final WebClient webClient = WebClient.create(mockWebServer.url("/").toString());

        memberRepository = mock(MemberRepository.class);
        memberCredentialsRepository = mock(MemberCredentialsRepository.class);
        notionConnectionClient = new NotionConnectionClient("clientId", "clientSecret", memberRepository, memberCredentialsRepository, webClient);
    }

    @AfterEach
    void shutDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("tistory의 RedirectUri를 만드는 테스트")
    void createRedirectUri() {
        //given
        //when
        //then
        Assertions.assertThat(notionConnectionClient.createRedirectUri("redirect_uri")).isEqualTo("https://api.notion.com/v1/oauth/authorize?client_id=clientId&redirect_uri=redirect_uri&response_type=code&owner=user");
    }

    @Test
    @DisplayName("notion에 accessToken을 save하는 테스트")
    void saveAccessToken() {
        //given
        final long memberId = 1L;
        final OAuthAccessTokenRequest oAuthAccessTokenRequest = new OAuthAccessTokenRequest("redirectUri", "code");
        final String accessToken = """
                {
                    "access_token": "accessToken"
                }
                """;
        final Member member = mock(Member.class);
        final MemberCredentials memberCredentials = mock(MemberCredentials.class);
        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
        given(memberCredentialsRepository.findMemberCredentialsByMember(member)).willReturn(Optional.of(memberCredentials));

        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(accessToken).addHeader("Content-Type", "application/json"));

        //when
        notionConnectionClient.saveAccessToken(memberId, oAuthAccessTokenRequest);

        //then
        then(memberCredentials).should(times(1)).updateNotionToken("accessToken");
    }

    @Test
    @DisplayName("notion에 accessToken을 제거하는 테스트")
    void deleteAccessTokenTest() {
        // Given
        final Long memberId = 1L;
        final Member member = mock(Member.class);
        final MemberCredentials memberCredentials = mock(MemberCredentials.class);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(memberCredentialsRepository.findMemberCredentialsByMember(member)).thenReturn(Optional.of(memberCredentials));

        // When
        notionConnectionClient.deleteAccessToken(memberId);

        // Then
        then(memberCredentials).should(times(1)).deleteNotionConnection();
    }
}