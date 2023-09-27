package org.donggle.backend.infrastructure.client.medium;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.ui.response.PublishResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MediumApiClientTest {
    private MediumApiClient mediumApiClient;
    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        final String mockServerUrl = mockWebServer.url("/").toString();
        mediumApiClient = new MediumApiClient(WebClient.create(mockServerUrl));
    }


    @AfterEach
    void shutDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("글이 정상적으로 발생됐는지 테스트")
    void publishTest() {
        //given
        final String accessToken = "testToken";
        final String content = "<p>Test Content</p>";
        final List<String> tags = List.of("football", "sport", "Liverpool");
        final String url = "https://medium.com/@majelbstoat/liverpool-fc-e6f36a";
        final LocalDateTime dateTime = Instant.ofEpochMilli(1442286338435L).atZone(ZoneId.systemDefault()).toLocalDateTime();
        final String responseBodyForUser = """
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
        final String responseBodyForPublish = """
                {
                    "data": {
                        "id": "e6f36a",
                        "title": "Liverpool FC",
                        "authorId": "5303d74c64f66366f00cb9b2a94f3251bf5",
                        "tags": ["football", "sport", "Liverpool"],
                        "url": "https://medium.com/@majelbstoat/liverpool-fc-e6f36a",
                        "canonicalUrl": "http://jamietalbot.com/posts/liverpool-fc",
                        "publishStatus": "public",
                        "publishedAt": 1442286338435,
                        "license": "all-rights-reserved",
                        "licenseUrl": "https://medium.com/policy/9db0094a1e0f"
                    }
                }
                """;

        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(responseBodyForUser).addHeader("Content-Type", "application/json; charset=utf-8"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(201).setBody(responseBodyForPublish).addHeader("Content-Type", "application/json; charset=utf-8"));


        final PublishResponse expectedResponse = PublishResponse.builder()
                .dateTime(dateTime)
                .tags(tags)
                .url(url)
                .build();

        final PublishRequest publishRequest = new PublishRequest(tags, "PUBLIC", "", "", "");

        //when
        final PublishResponse response = mediumApiClient.publish(accessToken, content, publishRequest, dateTime.toString());
        //then
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}