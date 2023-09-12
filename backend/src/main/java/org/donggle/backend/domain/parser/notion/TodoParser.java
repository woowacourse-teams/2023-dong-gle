package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.infrastructure.client.notion.dto.response.NotionBlockNodeResponse;
import org.donggle.backend.domain.writing.Style;

import java.util.List;

public record TodoParser(List<RichText> richTexts, boolean checked) implements NotionNormalBlockParser {
    public static TodoParser from(final NotionBlockNodeResponse blockNode) {
        final JsonNode blockProperties = blockNode.getBlockProperties();
        final List<RichText> richTexts = RichText.parseRichTexts(blockProperties, "rich_text");
        final boolean checked = blockProperties.get("checked").asBoolean();
        return new TodoParser(richTexts, checked);
    }

    @Override
    public String parseRawText() {
        return RichText.collectRawText(richTexts);
    }

    @Override
    public List<Style> parseStyles() {
        return RichText.parseStyles(richTexts);
    }
}
