package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleRange;
import org.donggle.backend.domain.writing.StyleType;
import org.donggle.backend.infrastructure.client.notion.dto.response.NotionBlockNodeResponse;

import java.util.List;

public record NotionBookmark(List<RichText> caption, String url) implements NotionNormalBlock {
    public static NotionBookmark from(final NotionBlockNodeResponse blockNode) {
        final JsonNode blockProperties = blockNode.getBlockProperties();
        final List<RichText> caption = RichText.parseRichTexts(blockProperties, "caption");
        final String url = blockProperties.get("url").asText();
        return new NotionBookmark(caption, url);
    }

    @Override
    public String parseRawText() {
        final String captionText = concatCaptionTexts();
        return url + captionText;
    }

    private String concatCaptionTexts() {
        return RichText.collectRawText(caption);
    }

    @Override
    public List<Style> parseStyles() {
        final int captionLength = caption.size();
        final int urlLength = url.length();
        if (captionLength == 0 && urlLength > 0) {
            return List.of(new Style(new StyleRange(0, 0), StyleType.LINK), new Style(new StyleRange(0, urlLength - 1), StyleType.LINK));
        }
        if (captionLength == 0) {
            return List.of(new Style(new StyleRange(0, 0), StyleType.LINK), new Style(new StyleRange(0, 0), StyleType.LINK));
        }
        final String concatenatedCaption = concatCaptionTexts();
        final int concatenatedLength = concatenatedCaption.length();
        return List.of(
                new Style(new StyleRange(0, urlLength - 1), StyleType.LINK),
                new Style(new StyleRange(urlLength, concatenatedLength + urlLength - 1), StyleType.LINK)
        );
    }
}
