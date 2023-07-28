package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record RichText(String plainText, String href, Annotations annotations) {
    public static List<RichText> parseRichTexts(final JsonNode blockProperties, final String richTextKey) {
        final List<JsonNode> richTextNodes = new ArrayList<>();
        blockProperties.withArray(richTextKey)
                .elements()
                .forEachRemaining(richTextNodes::add);

        return richTextNodes.stream()
                .map(RichText::from)
                .toList();
    }

    public static RichText from(final JsonNode richTextNode) {
        final String richTextType = richTextNode.get("type").asText();
        if (Objects.requireNonNull(richTextType).equals("text")) {
            final String plainText = richTextNode.get("plain_text").asText();
            final String href = richTextNode.get("href").asText();
            final Annotations annotations = Annotations.from(richTextNode.get("annotations"));

            return new RichText(plainText, href, annotations);
        }
        return new RichText("", "", Annotations.empty());
    }

    public static List<Style> parseStyles(final List<RichText> richTexts) {
        int offset = 0;
        final List<Style> styles = new ArrayList<>();
        for (final RichText richText : richTexts) {
            styles.addAll(richText.buildStyles(offset));
            offset += richText.plainText().length();
        }
        return styles;
    }

    public List<Style> buildStyles(final int offset) {
        final int end = offset + plainText.length() - 1;
        final List<Style> styles = new ArrayList<>();
        if (annotations.bold()) {
            styles.add(new Style(offset, end, StyleType.BOLD));
        }
        if (annotations.italic()) {
            styles.add(new Style(offset, end, StyleType.ITALIC));
        }
        if (annotations.code()) {
            styles.add(new Style(offset, end, StyleType.CODE));
        }
        if (!Objects.equals(href, "null")) {
            styles.add(new Style(offset, plainText.length() - 1, StyleType.LINK));
            styles.add(new Style(plainText.length(), href.length() - 1, StyleType.LINK));
        }
        return styles;
    }

    public static String collectRawText(final List<RichText> richTexts) {
        return richTexts.stream()
                .map(RichText::plainText)
                .reduce("", (a, b) -> a + b);
    }
}
