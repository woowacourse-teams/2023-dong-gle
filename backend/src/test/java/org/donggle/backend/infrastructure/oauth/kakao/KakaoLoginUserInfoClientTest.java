package org.donggle.backend.infrastructure.oauth.kakao;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.assertj.core.api.Assertions;
import org.donggle.backend.infrastructure.oauth.kakao.dto.response.UserInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.donggle.backend.domain.oauth.SocialType.KAKAO;

class KakaoLoginUserInfoClientTest {

    private KakaoLoginUserInfoClient kakaoLoginUserInfoClient;
    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        final WebClient webClient = WebClient.create(mockWebServer.url("/").toString());
        kakaoLoginUserInfoClient = new KakaoLoginUserInfoClient(webClient);
    }

    @AfterEach
    void shutDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("accessToken을 이용해 kakao에서 사용자의 정보를 가져오는 테스트")
    void request() {
        //given
        final String accessToken = "accessToken";
        final UserInfo userInfo = new UserInfo(123456789L, KAKAO, "홍길동");
        final String responseBody = """
                {
                    "id":123456789,
                    "connected_at": "2022-04-11T01:45:28Z",
                    "kakao_account": {
                        "profile_nickname_needs_agreement": false,
                        "profile": {
                            "nickname": "홍길동"
                        }
                    }
                }
                """;
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(responseBody).addHeader("Content-Type", "application/json"));
        //when
        final UserInfo request = kakaoLoginUserInfoClient.request(accessToken);
        //then
        Assertions.assertThat(request).usingRecursiveComparison().isEqualTo(userInfo);
    }
}