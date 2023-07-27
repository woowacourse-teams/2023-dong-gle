package org.donggle.backend.domain.parser.markdown;

import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

public class MarkDownStyleParser {
    private static final int INNER_TEXT = 2;

    public List<Style> extractStyles(String removedBlockTypeText, final String removedStyleTypeText) {
        final List<Style> styles = new ArrayList<>();

        for (final StyleType styleType : StyleType.values()) {
            final String targetStyleTypeText = extractByStyle(removedBlockTypeText, styleType);
            final Matcher matcher = styleType.getPattern().matcher(targetStyleTypeText);

            int currentIndex = 0;

            while (matcher.find()) {
                String matchedText = matcher.group(INNER_TEXT);
                if (!matchedText.isEmpty()) {
                    if (styleType == StyleType.LINK) {
                        final String caption = matcher.group(2);
                        final String url = matcher.group(4);

                        final int startIndex = removedStyleTypeText.indexOf(caption, currentIndex);
                        final int endIndex = startIndex + matchedText.length() - 1;
                        final Style style = new Style(startIndex, endIndex, styleType);
                        styles.add(style);
                        currentIndex = startIndex + 1;
                        matchedText = url;
                    }
                    final int startIndex = removedStyleTypeText.indexOf(matchedText, currentIndex);
                    final int endIndex = startIndex + matchedText.length() - 1;
                    final Style style = new Style(startIndex, endIndex, styleType);
                    styles.add(style);
                    currentIndex = startIndex + 1;
                }
            }
            removedBlockTypeText = removeStyles(removedBlockTypeText, styleType);
        }

        return styles;
    }

    private String extractByStyle(final String textBlock, final StyleType styleType) {
        final List<StyleType> styleTypes = new ArrayList<>(Arrays.asList(StyleType.values()));
        styleTypes.remove(styleType);
        return removeStyles(textBlock, styleTypes);
    }

    public String removeStyles(final String removedBlockTypeText) {
        final List<StyleType> styleTypes = Arrays.asList(StyleType.values());
        return removeStyles(removedBlockTypeText, styleTypes);
    }

    private String removeStyles(final String removedBlockTypeText, final List<StyleType> styleTypes) {
        final StringBuilder textBuilder = new StringBuilder(removedBlockTypeText);

        for (final StyleType styleType : styleTypes) {
            final Matcher matcher = styleType.getPattern().matcher(textBuilder);
            replaceStringBuilder(textBuilder, replaceAndAppend(matcher));
        }

        return textBuilder.toString();
    }

    private void replaceStringBuilder(final StringBuilder textBuilder, final String string) {
        textBuilder.setLength(0);
        textBuilder.append(string);
    }

    private String replaceAndAppend(final Matcher matcher) {
        final StringBuilder textBuilder = new StringBuilder();
        while (matcher.find()) {
            if (matcher.groupCount() == 5) {
                final String caption = matcher.group(2);
                final String url = matcher.group(4);
                final String matchedText = caption + url;
                if (!matchedText.isEmpty()) {
                    matcher.appendReplacement(textBuilder, Matcher.quoteReplacement(matchedText));
                }
                continue;
            }
            final String matchedText = matcher.group(2);
            if (!matchedText.isEmpty()) {
                matcher.appendReplacement(textBuilder, Matcher.quoteReplacement(matchedText));
            }
        }
        matcher.appendTail(textBuilder);
        return textBuilder.toString();
    }

    private String removeStyles(final String textBlock, final StyleType styleType) {
        return removeStyles(textBlock, List.of(styleType));
    }
}
