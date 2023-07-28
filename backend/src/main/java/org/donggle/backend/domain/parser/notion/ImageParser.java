package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Objects;

public record ImageParser(List<RichText> caption, String url) {

    public static ImageParser from(final JsonNode blockProperties) {
        final List<RichText> caption = RichText.parseRichTexts(blockProperties, "caption");
        final String type = blockProperties.get("type").asText();
        if (Objects.equals(type, "file")) {
            final String url = "";
            return new ImageParser(caption, url);
        }
        final String url = blockProperties.get("external").get("url").asText();
        return new ImageParser(caption, url);
    }

    public String parseCaption() {
        return RichText.collectRawText(caption);
    }
}
