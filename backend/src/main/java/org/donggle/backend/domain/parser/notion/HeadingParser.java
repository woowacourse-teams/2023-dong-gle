package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.application.service.notion.NotionBlockNode;
import org.donggle.backend.domain.writing.Style;

import java.util.List;

public record HeadingParser(List<RichText> richTexts, boolean isToggleable) implements NotionNormalBlockParser {
    public static NotionNormalBlockParser from(final NotionBlockNode blockNode) {
        final JsonNode blockProperties = blockNode.getBlockProperties();
        final List<RichText> richTexts = RichText.parseRichTexts(blockProperties, "rich_text");
        final boolean isToggleable = blockProperties.get("is_toggleable").asBoolean();
        return new HeadingParser(richTexts, isToggleable);
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
