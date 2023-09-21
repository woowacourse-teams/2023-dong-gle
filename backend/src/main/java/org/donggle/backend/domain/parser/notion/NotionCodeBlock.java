package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.infrastructure.client.notion.dto.response.NotionBlockNodeResponse;

import java.util.List;

public record NotionCodeBlock(List<RichText> richTexts, String language) {
    public static NotionCodeBlock from(final NotionBlockNodeResponse blockNode) {
        final JsonNode blockProperties = blockNode.getBlockProperties();
        final List<RichText> richTexts = RichText.parseRichTexts(blockProperties, "rich_text");
        final String language = blockProperties.get("language").asText();
        return new NotionCodeBlock(richTexts, language);
    }

    public String parseRawText() {
        return RichText.collectRawText(richTexts);
    }
}
