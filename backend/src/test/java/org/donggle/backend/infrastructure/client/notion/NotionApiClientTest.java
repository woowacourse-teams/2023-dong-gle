package org.donggle.backend.infrastructure.client.notion;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.donggle.backend.infrastructure.client.notion.dto.response.NotionBlockNodeResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class NotionApiClientTest {
    private NotionApiClient notionApiClient;
    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
        final WebClient webClient = WebClient.create(mockWebServer.url("/").toString());
        this.notionApiClient = new NotionApiClient(webClient);
    }

    @AfterEach
    void tearDown() throws IOException {
        this.mockWebServer.shutdown();
    }

    @Test
    @DisplayName("notion의 title을 찾는 테스트 추가")
    void testRetrieveParentBlockNode() {
        // Given
        final String blockId = "blockId";
        final String notionToken = "notionToken";

        final String responseContent = """
                {
                      "type": "child_database",
                      "child_database": {
                        "title": "title"
                      }
                }
                 """;

        mockWebServer.enqueue(new MockResponse().setBody(responseContent).addHeader("Content-Type", "application/json"));

        // When
        final NotionBlockNodeResponse result = notionApiClient.retrieveParentBlockNode(blockId, notionToken);

        // Then
        assertThat(result).isNotNull();
        assertThat(notionApiClient.findTitle(result)).isEqualTo("title");
    }
}