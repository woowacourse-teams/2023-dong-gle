package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleRange;
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

    private static RichText from(final JsonNode richTextNode) {
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
            if (!Objects.equals(richText.href, "null")) {
                offset += richText.href.length();
            }
            offset += richText.plainText().length();
        }
        return styles;
    }

    private List<Style> buildStyles(int offset) {
        final List<Style> styles = new ArrayList<>();
        if (!Objects.equals(href, "null")) {
            styles.add(new Style(new StyleRange(offset, offset + href.length() - 1), StyleType.LINK));
            offset += href.length();
            styles.add(new Style(new StyleRange(offset, offset + plainText.length() - 1), StyleType.LINK));
        }
        final int end = offset + plainText.length() - 1;
        if (annotations.bold()) {
            styles.add(new Style(new StyleRange(offset, end), StyleType.BOLD));
        }
        if (annotations.italic()) {
            styles.add(new Style(new StyleRange(offset, end), StyleType.ITALIC));
        }
        if (annotations.code()) {
            styles.add(new Style(new StyleRange(offset, end), StyleType.CODE));
        }
        if (annotations.strikethrough()) {
            styles.add(new Style(new StyleRange(offset, end), StyleType.STRIKETHROUGH));
        }
        if (annotations.underline()) {
            styles.add(new Style(new StyleRange(offset, end), StyleType.UNDERLINE));
        }
        return styles;
    }

    public static String collectRawText(final List<RichText> richTexts) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final RichText richText : richTexts) {
            if (!Objects.equals(richText.href, "null")) {
                stringBuilder.append(richText.href);
            }
            stringBuilder.append(richText.plainText());
        }
        return stringBuilder.toString();
    }
}
