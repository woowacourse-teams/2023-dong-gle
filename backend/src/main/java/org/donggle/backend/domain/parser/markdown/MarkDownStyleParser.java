package org.donggle.backend.domain.parser.markdown;

import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleRange;
import org.donggle.backend.domain.writing.StyleType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

@Component
public class MarkDownStyleParser {
    private static final int INNER_GROUP_INDEX = 2;
    private static final int CAPTION_GROUP_INDEX = 2;
    private static final int URL_GROUP_INDEX = 4;

    public List<Style> extractStyles(String removedBlockTypeText, final String removedStyleTypeText) {
        final List<Style> styles = new ArrayList<>();

        for (final StyleType styleType : StyleType.values()) {
            final String targetStyleTypeText = extractByStyle(removedBlockTypeText, styleType);
            final Matcher matcher = styleType.getPattern().matcher(targetStyleTypeText);

            int currentIndex = 0;

            while (matcher.find()) {
                String matchedText = matcher.group(INNER_GROUP_INDEX);
                if (!matchedText.isEmpty()) {
                    if (styleType == StyleType.LINK) {
                        final String caption = matcher.group(CAPTION_GROUP_INDEX);
                        final String url = matcher.group(URL_GROUP_INDEX);

                        final int startIndex = removedStyleTypeText.indexOf(url, currentIndex);
                        final int endIndex = startIndex + url.length() - 1;
                        final Style style = new Style(new StyleRange(startIndex, endIndex), styleType);
                        styles.add(style);
                        currentIndex = startIndex + 1;
                        matchedText = caption;
                    }
                    final int startIndex = removedStyleTypeText.indexOf(matchedText, currentIndex);
                    final int endIndex = startIndex + matchedText.length() - 1;
                    final Style style = new Style(new StyleRange(startIndex, endIndex), styleType);
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
            replaceStringBuilder(textBuilder, replaceAndAppend(matcher, styleType));
        }

        return textBuilder.toString();
    }

    private void replaceStringBuilder(final StringBuilder textBuilder, final String string) {
        textBuilder.setLength(0);
        textBuilder.append(string);
    }

    private String replaceAndAppend(final Matcher matcher, final StyleType styleType) {
        final StringBuilder textBuilder = new StringBuilder();
        while (matcher.find()) {
            replaceAndAppend(matcher, textBuilder, styleType);
        }
        matcher.appendTail(textBuilder);
        return textBuilder.toString();
    }

    private void replaceAndAppend(final Matcher matcher, final StringBuilder textBuilder, final StyleType styleType) {
        final String matchedText;
        if (Objects.requireNonNull(styleType) == StyleType.LINK) {
            final String caption = matcher.group(CAPTION_GROUP_INDEX);
            final String url = matcher.group(URL_GROUP_INDEX);
            matchedText = url + caption;
        } else {
            matchedText = matcher.group(INNER_GROUP_INDEX);
        }

        if (!matchedText.isEmpty()) {
            matcher.appendReplacement(textBuilder, Matcher.quoteReplacement(matchedText));
        }
    }

    private String removeStyles(final String textBlock, final StyleType styleType) {
        return removeStyles(textBlock, List.of(styleType));
    }
}
