package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.infrastructure.client.notion.dto.response.NotionBlockNodeResponse;

import java.util.List;

public record NotionCallout(List<RichText> richTexts, String icon) implements NotionNormalBlock {
    public static NotionNormalBlock from(final NotionBlockNodeResponse blockNode) {
        final JsonNode blockProperties = blockNode.getBlockProperties();
        final List<RichText> richTexts = RichText.parseRichTexts(blockProperties, "rich_text");
        String icon = "";
        if (blockProperties.get("icon").has("emoji")) {
            icon = blockProperties.get("icon").get("emoji").asText();
        }

        return new NotionCallout(richTexts, icon);
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
