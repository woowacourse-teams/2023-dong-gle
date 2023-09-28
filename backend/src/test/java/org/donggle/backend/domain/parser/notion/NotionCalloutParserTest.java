package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.infrastructure.client.notion.dto.response.NotionBlockNodeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class NotionCalloutParserTest {
    @Test
    @DisplayName("NotionBlockNode로부터 CalloutParser를 생성한다.")
    void from() {
        //given
        final JsonNode jsonNode = NotionBlockJsonBuilder.buildJsonBody("callout", false);

        //when
        final NotionNormalBlock blockParser = NotionCallout.from(new NotionBlockNodeResponse(jsonNode, 0));

        //then
        assertThat(blockParser.parseRawText()).isEqualTo("👉 call out");

    }

    @Test
    @DisplayName("CalloutParser로부터 Styles와 RawText를 파싱한다.")
    void parse() {
        //given
        final NotionCallout notionCalloutParser = new NotionCallout(List.of(
                new RichText("callout", "null", Annotations.empty())
        ), "💡");

        //when
        final String rawText = notionCalloutParser.parseRawText();
        final List<Style> styles = notionCalloutParser.parseStyles();

        //then
        final String expected = "💡 callout";
        assertAll(
                () -> assertThat(rawText).isEqualTo(expected),
                () -> assertThat(styles.isEmpty()).isTrue()
        );
    }
}