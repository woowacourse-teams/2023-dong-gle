package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleRange;
import org.donggle.backend.domain.writing.StyleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RichTextTest {
    private static final String RICH_TEXT_JSON = """
                {
                            "rich_text": [
                                {
                                    "type": "text",
                                    "text": {
                                        "content": "dfsdf",
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
                                    "plain_text": "dfsdf",
                                    "href": null
                                },
                                {
                                    "type": "text",
                                    "text": {
                                        "content": "안녕",
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
                                    "plain_text": "안녕",
                                    "href": null
                                }
                            ]
                }
            """;

    private JsonNode jsonNode;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        //RICH_TEXT_JSON to JsonNode
        final ObjectMapper objectMapper = new ObjectMapper();
        jsonNode = objectMapper.readTree(RICH_TEXT_JSON);
    }

    @Test
    @DisplayName("리치텍스트를 파싱하는 테스트")
    void parseRichTexts() {
        //given
        //when
        final List<RichText> richText = RichText.parseRichTexts(jsonNode, "rich_text");
        //then
        final List<RichText> expected = List.of(new RichText(
                "dfsdf",
                "null",
                new Annotations(true, true, false, false, false, "default")
        ), new RichText(
                "안녕",
                "null",
                new Annotations(false, false, false, false, false, "default")
        ));

        assertThat(richText).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("택스트 안에 스타일이 있을시 스타일을 파싱하는 테스트")
    void buildStyles() {
        //given
        final List<RichText> richText = RichText.parseRichTexts(jsonNode, "rich_text");

        //when
        final List<Style> styles = RichText.parseStyles(richText);
        final List<Style> expected = List.of(new Style(new StyleRange(0, 4), StyleType.BOLD), new Style(new StyleRange(0, 4), StyleType.ITALIC));

        //then
        assertThat(styles).usingRecursiveComparison().ignoringFields("createdAt", "updatedAt").isEqualTo(expected);
    }

    @Test
    @DisplayName("")
    void collectRawText() {
        //given
        final List<RichText> richText = RichText.parseRichTexts(jsonNode, "rich_text");

        //when
        final String rawText = RichText.collectRawText(richText);

        //then
        assertThat(rawText).isEqualTo("dfsdf안녕");
    }
}