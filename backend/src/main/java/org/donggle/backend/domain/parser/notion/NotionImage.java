package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.infrastructure.client.notion.dto.response.NotionBlockNodeResponse;

import java.util.List;
import java.util.Objects;

public record NotionImage(List<RichText> caption, String url) {

    public static NotionImage from(final NotionBlockNodeResponse blockNode) {
        final JsonNode blockProperties = blockNode.getBlockProperties();
        final List<RichText> caption = RichText.parseRichTexts(blockProperties, "caption");
        final String type = blockProperties.get("type").asText();
        if (Objects.equals(type, "file")) {
            final String url = "";
            return new NotionImage(caption, url);
        }
        final String url = blockProperties.get("external").get("url").asText();
        return new NotionImage(caption, url);
    }

    public String parseCaption() {
        return RichText.collectRawText(caption);
    }
}
