package org.donggle.backend.domain.renderer.html;

import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HtmlStyleRenderer {
    public String render(final String rawText, final List<Style> styles) {
        final Map<Integer, List<String>> startTags = new LinkedHashMap<>();
        final Map<Integer, List<String>> endTags = new LinkedHashMap<>();

        createTags(styles, startTags, endTags);

        return createHtmlStyleText(rawText, startTags, endTags);
    }

    private void createTags(final List<Style> styles, final Map<Integer, List<String>> startTags, final Map<Integer, List<String>> endTags) {
        boolean isPrevLink = false;
        for (final Style style : styles) {
            final HtmlStyleType htmlStyleType;
            if (style.getStyleType() == StyleType.LINK) {
                if (isPrevLink) {
                    htmlStyleType = HtmlStyleType.CAPTION;
                    isPrevLink = false;
                } else {
                    htmlStyleType = HtmlStyleType.LINK;
                    isPrevLink = true;
                }
            } else {
                htmlStyleType = HtmlStyleType.findByStyleType(style.getStyleType());
            }
            final int startIndex = style.getStartIndexValue();
            final int endIndex = style.getEndIndexValue();

            createStyleTag(startTags, htmlStyleType.getStartTag(), startIndex);
            createStyleTag(endTags, htmlStyleType.getEndTag(), endIndex);
        }
    }

    private void createStyleTag(final Map<Integer, List<String>> tags, final String styleTag, final int index) {
        List<String> startStyleTags = new ArrayList<>();
        if (tags.containsKey(index)) {
            startStyleTags = tags.get(index);
        }
        startStyleTags.add(styleTag);
        tags.put(index, startStyleTags);
    }

    private String createHtmlStyleText(final String rawText, final Map<Integer, List<String>> startTags, final Map<Integer, List<String>> endTags) {
        final StringBuilder result = new StringBuilder();

        for (int i = 0; i < rawText.length(); i++) {
            addTags(result, startTags, i);
            result.append(rawText.charAt(i));
            addTags(result, endTags, i);
        }

        return result.toString();
    }

    private void addTags(final StringBuilder result, final Map<Integer, List<String>> tags, final int index) {
        if (tags.containsKey(index)) {
            final List<String> startStyles = tags.get(index);
            for (final String startStyle : startStyles) {
                result.append(startStyle);
            }
        }
    }
}
