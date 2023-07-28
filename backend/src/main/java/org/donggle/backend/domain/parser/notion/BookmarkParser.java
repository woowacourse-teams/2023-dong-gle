package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleType;

import java.util.List;

public record BookmarkParser(List<RichText> caption, String url) implements NotionNormalBlockParser {
    public static BookmarkParser from(final JsonNode blockProperties) {
        final List<RichText> caption = RichText.parseRichTexts(blockProperties, "caption");
        final String url = blockProperties.get("url").asText();
        return new BookmarkParser(caption, url);
    }

    @Override
    public String parseRawText() {
        final String captionText = concatCaptionTexts();
        return captionText + url;
    }

    private String concatCaptionTexts() {
        return RichText.collectRawText(caption);
    }

    @Override
    public List<Style> parseStyles() {
        final int captionLength = caption.size();
        final int urlLength = url.length();
        if (captionLength == 0 && urlLength > 0) {
            return List.of(new Style(0, 0, StyleType.LINK), new Style(0, urlLength - 1, StyleType.LINK));
        }
        if (captionLength == 0) {
            return List.of(new Style(0, 0, StyleType.LINK), new Style(0, 0, StyleType.LINK));
        }
        final String concatenatedCaption = concatCaptionTexts();
        final int concatenatedLength = concatenatedCaption.length();
        return List.of(
                new Style(0, concatenatedLength - 1, StyleType.LINK),
                new Style(concatenatedLength, concatenatedLength + urlLength - 1, StyleType.LINK)
        );
    }
}
