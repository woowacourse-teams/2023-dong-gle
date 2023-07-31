package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.application.service.notion.NotionBlockNode;
import org.donggle.backend.domain.writing.Style;

import java.util.List;

public record CalloutParser(List<RichText> richTexts, String icon) implements NotionNormalBlockParser {
    public static NotionNormalBlockParser from(final NotionBlockNode blockNode) {
        final JsonNode blockProperties = blockNode.getBlockProperties();
        final List<RichText> richTexts = RichText.parseRichTexts(blockProperties, "rich_text");
        String icon = "";
        if (blockProperties.get("icon").has("emoji")) {
            icon = blockProperties.get("icon").get("emoji").asText();
        }

        return new CalloutParser(richTexts, icon);
    }

    @Override
    public String parseRawText() {
        return icon + " " + RichText.collectRawText(richTexts);
    }

    @Override
    public List<Style> parseStyles() {
        return RichText.parseStyles(richTexts);
    }
}
