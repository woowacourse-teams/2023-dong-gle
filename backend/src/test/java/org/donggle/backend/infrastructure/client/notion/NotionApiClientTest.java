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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NotionApiClientTest {
    private NotionApiClient notionApiClient;
    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
        final String baseUrl = mockWebServer.url("/").toString();
        final WebClient webClient = WebClient.create(baseUrl);
        this.notionApiClient = new NotionApiClient(webClient, baseUrl);
    }

    @AfterEach
    void tearDown() throws IOException {
        this.mockWebServer.shutdown();
    }

    @Test
    @DisplayName("notion의 title을 찾는 테스트 추가")
    void testRetrieveParentBlockNode() {
        // given
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

        // when
        final NotionBlockNodeResponse result = notionApiClient.retrieveParentBlockNode(blockId, notionToken);

        // then
        assertThat(result).isNotNull();
        assertThat(notionApiClient.findTitle(result)).isEqualTo("title");
    }

    @Test
    @DisplayName("notion의 블럭이 1개 일 때 테스트 추가")
    void retrieveChildrenBlocks_noChild() {
        // given
        final String blockId = "blockId";
        final String notionToken = "notionToken";

        final String responseTitle = """
                {
                	"object": "block",
                	"id": "c02fc1d3-db8b-45c5-a222-27595b15aea7",
                	"parent": {
                		"type": "page_id",
                		"page_id": "59833787-2cf9-4fdf-8782-e53db20768a5"
                	},
                	"created_time": "2022-03-01T19:05:00.000Z",
                	"last_edited_time": "2022-07-06T19:41:00.000Z",
                	"created_by": {
                		"object": "user",
                		"id": "ee5f0f84-409a-440f-983a-a5315961c6e4"
                	},
                	"last_edited_by": {
                		"object": "user",
                		"id": "ee5f0f84-409a-440f-983a-a5315961c6e4"
                	},
                	"has_children": true,
                	"archived": false,
                	"type": "child_database",
                	"child_database": {
                		"title": "title",
                		"color": "default",
                    "is_toggleable": false
                	}
                }
                 """;

        final String responseContent = """
                {
                	"object": "block",
                	"id": "c02fc1d3-db8b-45c5-a222-27595b15aea7",
                	"parent": {
                		"type": "page_id",
                		"page_id": "59833787-2cf9-4fdf-8782-e53db20768a5"
                	},
                	"created_time": "2022-03-01T19:05:00.000Z",
                	"last_edited_time": "2022-07-06T19:41:00.000Z",
                	"created_by": {
                		"object": "user",
                		"id": "ee5f0f84-409a-440f-983a-a5315961c6e4"
                	},
                	"last_edited_by": {
                		"object": "user",
                		"id": "ee5f0f84-409a-440f-983a-a5315961c6e4"
                	},
                	"has_more": false,
                	"next_cursor": null,
                	"has_children": false,
                	"archived": false,
                	"type": "heading_2",
                	"heading_2": {
                		"rich_text": [
                			{
                				"type": "text",
                				"text": {
                					"content": "Lacinato kale",
                					"link": null
                				},
                				"annotations": {
                					"bold": false,
                					"italic": false,
                					"strikethrough": false,
                					"underline": false,
                					"code": false,
                					"color": "green"
                				},
                				"plain_text": "Lacinato kale",
                				"href": null
                			}
                		],
                		"color": "default",
                    "is_toggleable": false
                	}
                }
                 """;


        mockWebServer.enqueue(new MockResponse().setBody(responseTitle).addHeader("Content-Type", "application/json"));
        mockWebServer.enqueue(new MockResponse().setBody(responseContent).addHeader("Content-Type", "application/json"));
        // when
        final NotionBlockNodeResponse notionBlockNodeResponse = notionApiClient.retrieveParentBlockNode(blockId, notionToken);
        final List<NotionBlockNodeResponse> notionBlockNodeResponses = notionApiClient.retrieveBodyBlockNodes(notionBlockNodeResponse, notionToken);

        // then
        assertThat(notionBlockNodeResponses).hasSize(1);
    }

    @Test
    @DisplayName("notion의 블럭이 2개 이상 일 때 테스트 추가")
    void retrieveChildrenBlocks_isChild() {
        // given
        final String blockId = "blockId";
        final String notionToken = "notionToken";

        final String responseTitle = """
                {
                	"object": "block",
                	"id": "c02fc1d3-db8b-45c5-a222-27595b15aea7",
                	"parent": {
                		"type": "page_id",
                		"page_id": "59833787-2cf9-4fdf-8782-e53db20768a5"
                	},
                	"created_time": "2022-03-01T19:05:00.000Z",
                	"last_edited_time": "2022-07-06T19:41:00.000Z",
                	"created_by": {
                		"object": "user",
                		"id": "ee5f0f84-409a-440f-983a-a5315961c6e4"
                	},
                	"last_edited_by": {
                		"object": "user",
                		"id": "ee5f0f84-409a-440f-983a-a5315961c6e4"
                	},
                	"has_children": true,
                	"archived": false,
                	"type": "child_database",
                	"child_database": {
                		"title": "title",
                		"color": "default",
                    "is_toggleable": false
                	}
                }
                 """;

        final String responseContent = """
                {
                "object": "list",
                      "results": [
                          {
                              "object": "block",
                              "id": "417e9754-5e9a-44d4-a3f5-6cc87d990efb",
                              "parent": {
                                  "type": "page_id",
                                  "page_id": "f4f5fc0f-ae70-412b-ba70-a73d972af6b3"
                              },
                              "created_time": "2023-09-25T13:25:00.000Z",
                              "last_edited_time": "2023-09-25T13:32:00.000Z",
                              "created_by": {
                                  "object": "user",
                                  "id": "aaea0d4a-255d-4709-8ad5-ec436c001bdb"
                              },
                              "last_edited_by": {
                                  "object": "user",
                                  "id": "aaea0d4a-255d-4709-8ad5-ec436c001bdb"
                              },
                              "has_children": false,
                              "archived": false,
                              "type": "heading_2",
                              "heading_2": {
                                  "rich_text": [
                                      {
                                          "type": "text",
                                          "text": {
                                              "content": "외부 서버 Mocking",
                                              "link": null
                                          },
                                          "annotations": {
                                              "bold": false,
                                              "italic": false,
                                              "strikethrough": false,
                                              "underline": false,
                                              "code": false,
                                              "color": "default"
                                          },
                                          "plain_text": "외부 서버 Mocking",
                                          "href": null
                                      }
                                  ],
                                  "is_toggleable": false,
                                  "color": "default"
                              }
                          },
                          {
                              "object": "block",
                              "id": "aa75dab8-e61d-4578-a1a4-d7b3517656a7",
                              "parent": {
                                  "type": "page_id",
                                  "page_id": "f4f5fc0f-ae70-412b-ba70-a73d972af6b3"
                              },
                              "created_time": "2023-09-25T13:25:00.000Z",
                              "last_edited_time": "2023-09-25T13:32:00.000Z",
                              "created_by": {
                                  "object": "user",
                                  "id": "aaea0d4a-255d-4709-8ad5-ec436c001bdb"
                              },
                              "last_edited_by": {
                                  "object": "user",
                                  "id": "aaea0d4a-255d-4709-8ad5-ec436c001bdb"
                              },
                              "has_children": false,
                              "archived": false,
                              "type": "paragraph",
                              "paragraph": {
                                  "rich_text": [
                                      {
                                          "type": "text",
                                          "text": {
                                              "content": "외부 서버는 우리가 제어할 수 있는 대상이 아니기 때문에 Mocking하고 우리의 로직만을 테스트 한다면 완벽하게 동작해 라고는 말할 수 없지만 카카오 API가 문제 없으면 동작할거야 ****라고 말할 순 있을 것 같아요.",
                                              "link": null
                                          },
                                          "annotations": {
                                              "bold": false,
                                              "italic": false,
                                              "strikethrough": false,
                                              "underline": false,
                                              "code": false,
                                              "color": "default"
                                          },
                                          "plain_text": "외부 서버는 우리가 제어할 수 있는 대상이 아니기 때문에 Mocking하고 우리의 로직만을 테스트 한다면 완벽하게 동작해 라고는 말할 수 없지만 카카오 API가 문제 없으면 동작할거야 ****라고 말할 순 있을 것 같아요.",
                                          "href": null
                                      }
                                  ],
                                  "color": "default"
                              }
                          },
                          {
                              "object": "block",
                              "id": "ed6383d3-80f7-4aa1-90f9-a2b58c458292",
                              "parent": {
                                  "type": "page_id",
                                  "page_id": "f4f5fc0f-ae70-412b-ba70-a73d972af6b3"
                              },
                              "created_time": "2023-09-25T13:32:00.000Z",
                              "last_edited_time": "2023-09-28T01:04:00.000Z",
                              "created_by": {
                                  "object": "user",
                                  "id": "aaea0d4a-255d-4709-8ad5-ec436c001bdb"
                              },
                              "last_edited_by": {
                                  "object": "user",
                                  "id": "aaea0d4a-255d-4709-8ad5-ec436c001bdb"
                              },
                              "has_children": false,
                              "archived": false,
                              "type": "paragraph",
                              "paragraph": {
                                  "rich_text": [],
                                  "color": "default"
                              }
                          },
                          {
                              "object": "block",
                              "id": "24d82e43-a2b3-420a-9fc0-91effeee4e80",
                              "parent": {
                                  "type": "page_id",
                                  "page_id": "f4f5fc0f-ae70-412b-ba70-a73d972af6b3"
                              },
                              "created_time": "2023-09-25T13:32:00.000Z",
                              "last_edited_time": "2023-09-28T01:04:00.000Z",
                              "created_by": {
                                  "object": "user",
                                  "id": "aaea0d4a-255d-4709-8ad5-ec436c001bdb"
                              },
                              "last_edited_by": {
                                  "object": "user",
                                  "id": "aaea0d4a-255d-4709-8ad5-ec436c001bdb"
                              },
                              "has_children": false,
                              "archived": false,
                              "type": "paragraph",
                              "paragraph": {
                                  "rich_text": [],
                                  "color": "default"
                              }
                          }
                          ],
                              "next_cursor": null,
                              "has_more": false,
                              "type": "block",
                              "block": {},
                              "developer_survey": "https://notionup.typeform.com/to/bllBsoI4?utm_source=postman"
                          }
                 """;

        mockWebServer.enqueue(new MockResponse().setBody(responseTitle).addHeader("Content-Type", "application/json"));
        mockWebServer.enqueue(new MockResponse().setBody(responseContent).addHeader("Content-Type", "application/json"));

        // when
        final NotionBlockNodeResponse notionBlockNodeResponse = notionApiClient.retrieveParentBlockNode(blockId, notionToken);
        final List<NotionBlockNodeResponse> notionBlockNodeResponses = notionApiClient.retrieveBodyBlockNodes(notionBlockNodeResponse, notionToken);

        // then
        assertThat(notionBlockNodeResponses).hasSize(5);
    }
}
