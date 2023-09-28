package org.donggle.backend.infrastructure.oauth.kakao;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

class KakaoLoginTokenClientTest {
    private KakaoLoginTokenClient kakaoLoginTokenClient;
    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        final WebClient webClient = WebClient.create(mockWebServer.url("/").toString());
        kakaoLoginTokenClient = new KakaoLoginTokenClient(
                "clientId",
                "clientSecret",
                webClient);
    }

    @AfterEach
    void shutDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("RedirectUri를 정상적으로 만들어주는지 테스트")
    void createRedirectUri() {
        //given
        //when
        //then
        Assertions.assertThat(kakaoLoginTokenClient.createRedirectUri("redirect_uri")).isEqualTo("https://kauth.kakao.com/oauth/authorize?client_id=clientId&redirect_uri=redirect_uri&response_type=code");
    }

    @Test
    @DisplayName("kakao로그인 시 accessToken 발급 테스트")
    void request() {
        //given
        final String redirectUri = "redirect_uri";
        final String code = "code";
        final String responseBody = """
                {
                    "token_type":"bearer",
                    "access_token":"ACCESS_TOKEN",
                    "expires_in":43199,
                    "refresh_token":"${REFRESH_TOKEN}",
                    "refresh_token_expires_in":5184000,
                    "scope":"account_email profile"
                }
                """;
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(responseBody).addHeader("Content-Type", "application/json"));
        //when


        final String request = kakaoLoginTokenClient.request(code, redirectUri);
        //then
        Assertions.assertThat(request).isEqualTo("ACCESS_TOKEN");
    }
}