package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.infrastructure.client.notion.dto.response.NotionBlockNodeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.donggle.backend.domain.parser.notion.NotionBlockJsonBuilder.buildJsonBody;

class NotionHeadingParserTest {
    @Test
    @DisplayName("notionBlockNode로부터 NotionNormalBlockParser를 생성한다.")
    void from() {
        //given
        final JsonNode jsonNode = buildJsonBody("heading_1", false);

        //when
        final NotionNormalBlock blockParser = NotionHeading.from(new NotionBlockNodeResponse(jsonNode, 0));

        //then
        assertThat(blockParser.parseRawText()).isEqualTo("heading");
    }
}