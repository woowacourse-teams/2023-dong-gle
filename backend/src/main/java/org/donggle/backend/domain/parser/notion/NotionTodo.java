package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.infrastructure.client.notion.dto.response.NotionBlockNodeResponse;

import java.util.List;

public record NotionTodo(List<RichText> richTexts, boolean checked) implements NotionNormalBlock {
    public static NotionTodo from(final NotionBlockNodeResponse blockNode) {
        final JsonNode blockProperties = blockNode.getBlockProperties();
        final List<RichText> richTexts = RichText.parseRichTexts(blockProperties, "rich_text");
        final boolean checked = blockProperties.get("checked").asBoolean();
        return new NotionTodo(richTexts, checked);
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
