package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.infrastructure.client.notion.dto.response.NotionBlockNodeResponse;

import java.util.List;

public record NotionImage(List<RichText> caption, String url, FileType fileType) {

    public static NotionImage from(final NotionBlockNodeResponse blockNode) {
        final JsonNode blockProperties = blockNode.getBlockProperties();
        final List<RichText> caption = RichText.parseRichTexts(blockProperties, "caption");
        final String type = blockProperties.get("type").asText();
        return switch (FileType.valueOf(type.toUpperCase())) {
            case FILE -> {
                final String url = blockProperties.get("file").get("url").asText();
                yield new NotionImage(caption, url, FileType.FILE);
            }
            case EXTERNAL -> {
                final String url = blockProperties.get("external").get("url").asText();
                yield new NotionImage(caption, url, FileType.EXTERNAL);
            }
        };
    }

    public String parseCaption() {
        return RichText.collectRawText(caption);
    }
}
