package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.application.service.notion.NotionBlockNode;

import java.util.List;

public record CodeBlockParser(List<RichText> richTexts, String language) {
    public static CodeBlockParser from(final NotionBlockNode blockNode) {
        final JsonNode blockProperties = blockNode.getBlockProperties();
        final List<RichText> richTexts = RichText.parseRichTexts(blockProperties, "rich_text");
        final String language = blockProperties.get("language").asText();
        return new CodeBlockParser(richTexts, language);
    }

    public String parseRawText() {
        return RichText.collectRawText(richTexts);
    }
}
