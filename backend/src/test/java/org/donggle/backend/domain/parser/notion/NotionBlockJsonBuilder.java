package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NotionBlockJsonBuilder {
    private static final String BLOCK_JSON = """
            {
                        "object": "block",
                        "id": "90dced31-6888-4ad8-9d5b-c7da671786c0",
                        "parent": {
                            "type": "page_id",
                            "page_id": "87f18075-4f0d-47e5-9d2d-92c6c91792e0"
                        },
                        "created_time": "2023-07-27T08:33:00.000Z",
                        "last_edited_time": "2023-07-27T08:33:00.000Z",
                        "created_by": {
                            "object": "user",
                            "id": "b138e1c9-4054-4713-bd7b-62af6d7641fb"
                        },
                        "last_edited_by": {
                            "object": "user",
                            "id": "b138e1c9-4054-4713-bd7b-62af6d7641fb"
                        },
                        "has_children": ${has_children},
                        "archived": false,
                        "type": "${type}",
                        "${type}": ${body}
                    }
            """;
    private static final String BOOKMARK_JSON_BODY = """
            {
                "caption": [],
                "url": "abc.com"
            }
            """;
    private static final String CODE_JSON_BODY = """
            {
                "caption": [],
                "rich_text": [
                    {
                        "type": "text",
                        "text": {
                            "block": "void main()",
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
                        "plain_text": "void main()",
                        "href": null
                    }
                ],
                "language": "java"
            }
            """;
    private static final String IMAGE_JSON_BODY = """
            {
                "caption": [
                {
                    "type": "text",
                    "text": {
                        "block": "heel",
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
                    "plain_text": "heel",
                    "href": null
                },
                {
                    "type": "text",
                    "text": {
                        "block": "oo",
                        "link": null
                    },
                    "annotations": {
                        "bold": true,
                        "italic": false,
                        "strikethrough": false,
                        "underline": false,
                        "code": false,
                        "color": "default"
                    },
                    "plain_text": "oo",
                    "href": null
                }],
                "type": "external",
                "external": {
                    "url": "https://www.notion.so/image.png"
                }
            }
            """;
    private static final String PARAGRAPH_JSON_BODY = """
            {
                "rich_text": [
                    {
                        "type": "text",
                        "text": {
                            "block": "this is just normal paragraph ",
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
                        "plain_text": "this is just normal paragraph ",
                        "href": null
                    },
                    {
                        "type": "text",
                        "text": {
                            "block": "bold",
                            "link": null
                        },
                        "annotations": {
                            "bold": true,
                            "italic": false,
                            "strikethrough": false,
                            "underline": false,
                            "code": false,
                            "color": "default"
                        },
                        "plain_text": "bold",
                        "href": null
                    },
                    {
                        "type": "text",
                        "text": {
                            "block": " ",
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
                        "plain_text": " ",
                        "href": null
                    },
                    {
                        "type": "text",
                        "text": {
                            "block": "italic",
                            "link": null
                        },
                        "annotations": {
                            "bold": false,
                            "italic": true,
                            "strikethrough": false,
                            "underline": false,
                            "code": false,
                            "color": "default"
                        },
                        "plain_text": "italic",
                        "href": null
                    },
                    {
                        "type": "text",
                        "text": {
                            "block": " ",
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
                        "plain_text": " ",
                        "href": null
                    },
                    {
                        "type": "text",
                        "text": {
                            "block": "bolditalic",
                            "link": null
                        },
                        "annotations": {
                            "bold": true,
                            "italic": true,
                            "strikethrough": false,
                            "underline": false,
                            "code": false,
                            "color": "default"
                        },
                        "plain_text": "bolditalic",
                        "href": null
                    },
                    {
                        "type": "text",
                        "text": {
                            "block": " ",
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
                        "plain_text": " ",
                        "href": null
                    },
                    {
                        "type": "text",
                        "text": {
                            "block": "code",
                            "link": null
                        },
                        "annotations": {
                            "bold": false,
                            "italic": false,
                            "strikethrough": false,
                            "underline": false,
                            "code": true,
                            "color": "default"
                        },
                        "plain_text": "code",
                        "href": null
                    },
                    {
                        "type": "text",
                        "text": {
                            "block": " ",
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
                        "plain_text": " ",
                        "href": null
                    },
                    {
                        "type": "text",
                        "text": {
                            "block": "codehasbold",
                            "link": null
                        },
                        "annotations": {
                            "bold": true,
                            "italic": false,
                            "strikethrough": false,
                            "underline": false,
                            "code": true,
                            "color": "default"
                        },
                        "plain_text": "codehasbold",
                        "href": null
                    },
                    {
                        "type": "text",
                        "text": {
                            "block": " ",
                            "link": null
                        },
                        "annotations": {
                            "bold": true,
                            "italic": false,
                            "strikethrough": false,
                            "underline": false,
                            "code": false,
                            "color": "default"
                        },
                        "plain_text": " ",
                        "href": null
                    },
                    {
                        "type": "text",
                        "text": {
                            "block": "codehasitalicbold",
                            "link": null
                        },
                        "annotations": {
                            "bold": true,
                            "italic": true,
                            "strikethrough": false,
                            "underline": false,
                            "code": true,
                            "color": "default"
                        },
                        "plain_text": "codehasitalicbold",
                        "href": null
                    }
                ],
                "color": "default"
            }
            """;
    private static final String CALL_OUT_JSON_BODY = """
              {
                "rich_text": [
                    {
                        "type": "text",
                        "text": {
                            "block": "call out",
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
                        "plain_text": "call out",
                        "href": null
                    }
                ],
                "icon": {
                    "type": "emoji",
                    "emoji": "👉"
                },
                "color": "gray_background"
            }
               """;

    private static final String HEADING_JSON_BODY = """
            {
                "rich_text": [
                    {
                        "type": "text",
                        "text": {
                            "block": "heading",
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
                        "plain_text": "heading",
                        "href": null
                    }
                ],
                "is_toggleable": false,
                "color": "default"
            }
            """;

    public static JsonNode buildJsonBody(final String type, final boolean hasChildren) {
        final String blockJson = BLOCK_JSON.replace("${has_children}", String.valueOf(hasChildren));
        final String body = switch (type) {
            case "paragraph" ->
                    blockJson.replaceAll("\\$\\{type}", "paragraph").replace("${body}", PARAGRAPH_JSON_BODY);
            case "image" -> blockJson.replaceAll("\\$\\{type}", "image").replace("${body}", IMAGE_JSON_BODY);
            case "code" -> blockJson.replaceAll("\\$\\{type}", "code").replace("${body}", CODE_JSON_BODY);
            case "bookmark" -> blockJson.replaceAll("\\$\\{type}", "bookmark").replace("${body}", BOOKMARK_JSON_BODY);
            case "callout" -> blockJson.replaceAll("\\$\\{type}", "callout").replace("${body}", CALL_OUT_JSON_BODY);
            case "heading_1" -> blockJson.replaceAll("\\$\\{type}", "heading_1").replace("${body}", HEADING_JSON_BODY);
            case "divider" -> blockJson.replaceAll("\\$\\{type}", "divider").replace("${body}", "[]");
            default -> "";
        };
        try {
            return new ObjectMapper().readTree(body);
        } catch (final JsonProcessingException ignored) {
        }
        return null;
    }
}
