package org.donggle.backend.domain.parser;

import org.donggle.backend.domain.Style;
import org.donggle.backend.domain.StyleType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkDownStyleParser {

    private static final int INNER_TEXT = 2;
    private static final int INNER_TEXT_STANDARD = 2;

    public String removeAllStyles(final String textBlock) {
        StringBuilder builder = new StringBuilder(textBlock);
        final StringBuilder replaceBuilder = new StringBuilder();

        for (final StyleType type : StyleType.values()) {
            final Pattern pattern = type.getPattern();
            final Matcher matcher = pattern.matcher(builder.toString());

            while (matcher.find() && matcher.groupCount() >= INNER_TEXT_STANDARD) {
                final String extractedText = matcher.group(INNER_TEXT);
                matcher.appendReplacement(replaceBuilder, Matcher.quoteReplacement(extractedText));
            }

            matcher.appendTail(replaceBuilder);
            builder = new StringBuilder(replaceBuilder);
            replaceBuilder.setLength(0);
        }
        return builder.toString();
    }

    public List<Style> extractStyles(String textBlock, final String originalText) {
        final List<Style> styles = new ArrayList<>();

        for (final StyleType styleType : StyleType.values()) {
            final String cleanedInput = removeUnmatchedStyle(textBlock, styleType);
            final Matcher matcher = styleType.getPattern().matcher(cleanedInput);

            while (matcher.find() && matcher.groupCount() >= INNER_TEXT_STANDARD) {
                final String matchedText = matcher.group(INNER_TEXT);
                final int start = originalText.indexOf(matchedText);
                final int end = start + matchedText.length() - 1;
                final Style style = new Style(start, end, styleType);
                styles.add(style);
            }
            textBlock = removePartStyles(textBlock, styleType);
        }

        return styles;
    }

    private String removePartStyles(final String textBlock, final StyleType styleType) {
        final StringBuilder builder = new StringBuilder(textBlock);
        final Matcher matcher = styleType.getPattern().matcher(builder);
        final StringBuilder replacementBuilder = new StringBuilder();

        while (matcher.find() && matcher.groupCount() >= INNER_TEXT_STANDARD) {
            final String extractedText = matcher.group(INNER_TEXT);
            matcher.appendReplacement(replacementBuilder, Matcher.quoteReplacement(extractedText));
        }

        matcher.appendTail(replacementBuilder);
        return builder.toString();
    }

    private String removeUnmatchedStyle(final String textBlock, final StyleType styleType) {
        StringBuilder builder = new StringBuilder(textBlock);
        final StringBuilder replacementBuilder = new StringBuilder();

        for (final StyleType type : StyleType.values()) {
            if (type != styleType) {
                final Matcher matcher = type.getPattern().matcher(builder.toString());

                while (matcher.find() && matcher.groupCount() >= INNER_TEXT_STANDARD) {
                    final String extractedText = matcher.group(INNER_TEXT);
                    if (!extractedText.isEmpty()) {
                        matcher.appendReplacement(replacementBuilder, Matcher.quoteReplacement(extractedText));
                    }
                }

                matcher.appendTail(replacementBuilder);
                builder = new StringBuilder(replacementBuilder);
                replacementBuilder.setLength(0);
            }
        }
        return builder.toString();
    }
}
