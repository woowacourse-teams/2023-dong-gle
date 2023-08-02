package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.application.service.notion.NotionBlockNode;
import org.donggle.backend.domain.writing.Style;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CalloutParserTest {
    @Test
    @DisplayName("NotionBlockNode로부터 CalloutParser를 생성한다.")
    void from() {
        //given
        final JsonNode jsonNode = NotionBlockJsonBuilder.buildJsonBody("callout", false);

        //when
        final NotionNormalBlockParser blockParser = CalloutParser.from(new NotionBlockNode(jsonNode, 0));

        //then
        assertThat(blockParser.parseRawText()).isEqualTo("👉 call out");

    }

    @Test
    @DisplayName("CalloutParser로부터 Styles와 RawText를 파싱한다.")
    void parse() {
        //given
        final CalloutParser calloutParser = new CalloutParser(List.of(
                new RichText("callout", "null", Annotations.empty())
        ), "💡");

        //when
        final String rawText = calloutParser.parseRawText();
        final List<Style> styles = calloutParser.parseStyles();

        //then
        final String expected = "💡 callout";
        assertAll(
                () -> assertThat(rawText).isEqualTo(expected),
                () -> assertThat(styles.isEmpty()).isTrue()
        );
    }
}