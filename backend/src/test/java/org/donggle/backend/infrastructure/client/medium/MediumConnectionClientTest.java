package org.donggle.backend.infrastructure.client.medium;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.assertj.core.api.Assertions;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.service.request.TokenAddRequest;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.infrastructure.client.exception.ClientInvalidTokenException;
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

class MediumConnectionClientTest {
    private MemberRepository memberRepository;
    private MemberCredentialsRepository memberCredentialsRepository;
    private MockWebServer mockWebServer;
    private MediumConnectionClient mediumConnectionClient;

    @BeforeEach
    void setUp() throws IOException {
        memberCredentialsRepository = mock(MemberCredentialsRepository.class);
        memberRepository = mock(MemberRepository.class);

        mockWebServer = new MockWebServer();
        mockWebServer.start();

        final WebClient webClient = WebClient.create(mockWebServer.url("/").toString());
        mediumConnectionClient = new MediumConnectionClient(
                memberRepository,
                memberCredentialsRepository,
                webClient);
    }

    @AfterEach
    void shutDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("medium의 accessToken을 save하는 테스트")
    void saveAccessToken() {
        //given
        final long memberId = 1L;
        final TokenAddRequest token = new TokenAddRequest("token");
        final String userInfo = """
                {
                  "data": {
                    "id": "5303d74c64f66366f00cb9b2a94f3251bf5",
                    "username": "majelbstoat",
                    "name": "Jamie Talbot",
                    "url": "https://medium.com/@majelbstoat",
                    "imageUrl": "https://images.medium.com/0*fkfQiTzT7TlUGGyI.png"
                  }
                }
                """;
        final Member member = mock(Member.class);
        final MemberCredentials memberCredentials = mock(MemberCredentials.class);
        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
        given(memberCredentialsRepository.findByMember(member)).willReturn(Optional.of(memberCredentials));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(userInfo).addHeader("Content-Type", "application/json"));
        mediumConnectionClient.saveAccessToken(memberId, token);

        then(memberCredentials).should(times(1)).updateMediumToken(token.token());
    }

    @Test
    @DisplayName("medium의 accessToken을 save하는도중 에외 테스트")
    void saveAccessToken_status_400() {
        //given
        final long memberId = 1L;
        final TokenAddRequest token = new TokenAddRequest("token");
        final String userInfo = """
                {
                  "data": {
                    "id": "5303d74c64f66366f00cb9b2a94f3251bf5",
                    "username": "majelbstoat",
                    "name": "Jamie Talbot",
                    "url": "https://medium.com/@majelbstoat",
                    "imageUrl": "https://images.medium.com/0*fkfQiTzT7TlUGGyI.png"
                  }
                }
                """;
        mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(userInfo).addHeader("Content-Type", "application/json"));

        Assertions.assertThatThrownBy(
                () -> mediumConnectionClient.saveAccessToken(memberId, token)
        ).isInstanceOf(ClientInvalidTokenException.class);
    }

    @Test
    @DisplayName("medium의 accessToken을 제거하는 테스트")
    void deleteAccessTokenTest() {
        // Given
        final Long memberId = 1L;
        final Member member = mock(Member.class);
        final MemberCredentials memberCredentials = mock(MemberCredentials.class);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(memberCredentialsRepository.findByMember(member)).thenReturn(Optional.of(memberCredentials));

        // When
        mediumConnectionClient.deleteAccessToken(memberId);

        // Then
        then(memberCredentials).should(times(1)).deleteMediumConnection();
    }
}