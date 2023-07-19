package org.donggle.backend.domain.parser;

import org.donggle.backend.domain.Style;
import org.donggle.backend.domain.StyleType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

public class MarkDownStyleParser {

    private static final int INNER_TEXT = 2;

    public List<Style> extractStyles(String textBlock, final String originalText) {
        final List<Style> styles = new ArrayList<>();

        for (final StyleType styleType : StyleType.values()) {
            final String extractedByStyle = extractByStyle(textBlock, styleType);
            final Matcher matcher = styleType.getPattern().matcher(extractedByStyle);

            while (matcher.find()) {
                final String matchedText = matcher.group(INNER_TEXT);
                if (!matchedText.isEmpty()) {
                    final int startIndex = originalText.indexOf(matchedText);
                    final int endIndex = startIndex + matchedText.length() - 1;
                    final Style style = new Style(startIndex, endIndex, styleType);
                    styles.add(style);
                }
            }
            textBlock = removeStyles(textBlock, styleType);
        }

        return styles;
    }


    private String extractByStyle(final String textBlock, final StyleType styleType) {
        final List<StyleType> styleTypes = new ArrayList<>(Arrays.asList(StyleType.values()));
        styleTypes.remove(styleType);
        return removeStyles(textBlock, styleTypes);
    }

    public String removeStyles(final String textBlock) {
        final List<StyleType> styleTypes = Arrays.asList(StyleType.values());
        return removeStyles(textBlock, styleTypes);
    }

    private String removeStyles(final String textBlock, final List<StyleType> styleTypes) {
        final StringBuilder textBuilder = new StringBuilder(textBlock);

        for (StyleType type : styleTypes) {
            final Matcher matcher = type.getPattern().matcher(textBuilder);
            replaceStringBuilder(textBuilder, replaceAndAppend(matcher));
        }

        return textBuilder.toString();
    }

    private void replaceStringBuilder(final StringBuilder stringBuilder, final String string) {
        stringBuilder.setLength(0);
        stringBuilder.append(string);
    }

    private String replaceAndAppend(final Matcher matcher) {
        final StringBuilder builder = new StringBuilder();
        while (matcher.find()) {
            final String matchedText = matcher.group(INNER_TEXT);
            if (!matchedText.isEmpty()) {
                matcher.appendReplacement(builder, Matcher.quoteReplacement(matchedText));
            }
        }
        matcher.appendTail(builder);
        return builder.toString();
    }

    private String removeStyles(final String textBlock, final StyleType styleType) {
        return removeStyles(textBlock, List.of(styleType));
    }
}
