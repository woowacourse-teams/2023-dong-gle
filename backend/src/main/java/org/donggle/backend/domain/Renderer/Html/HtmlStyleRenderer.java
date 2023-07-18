package org.donggle.backend.domain.Renderer.Html;

import org.donggle.backend.domain.Style;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HtmlStyleRenderer {
    public String render(final String rawText, final List<Style> styles) {
        Map<Integer, List<String>> startTags = new LinkedHashMap<>();
        Map<Integer, List<String>> endTags = new LinkedHashMap<>();

        createTags(styles, startTags, endTags);

        return createHtmlStyleText(rawText, startTags, endTags);
    }

    private void createTags(final List<Style> styles, final Map<Integer, List<String>> startTag, final Map<Integer, List<String>> endTag) {
        for (final Style style : styles) {
            HtmlStyleType htmlStyleType = HtmlStyleType.findByStyleType(style.getStyleType());
            int startIndex = style.getStartIndex();
            int endIndex = style.getEndIndex();

            createStartStyleTag(startTag, htmlStyleType.getStartTag(), startIndex);
            createEndStyleTag(endTag, htmlStyleType.getEndTag(), endIndex);
        }
    }

    private void createStartStyleTag(final Map<Integer, List<String>> startTag, final String styleStartTag, final int startIndex) {
        List<String> startStyleTags = new ArrayList<>();
        if (startTag.containsKey(startIndex)) {
            startStyleTags = startTag.get(startIndex);
        }
        startStyleTags.add(styleStartTag);
        startTag.put(startIndex, startStyleTags);
    }

    private void createEndStyleTag(final Map<Integer, List<String>> endTag, final String styleEndTag, final int endIndex) {
        List<String> endStyleTags = new ArrayList<>();
        if (endTag.containsKey(endIndex)) {
            endStyleTags = endTag.get(endIndex);
        }
        endStyleTags.add(styleEndTag);
        endTag.put(endIndex, endStyleTags);
    }

    private String createHtmlStyleText(final String rawText, final Map<Integer, List<String>> startTags, final Map<Integer, List<String>> endTags) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < rawText.length(); i++) {
            addTags(result, startTags, i);
            result.append(rawText.charAt(i));
            addTags(result, endTags, i);
        }

        return String.valueOf(result);
    }

    private void addTags(final StringBuilder result, final Map<Integer, List<String>> startTags, final int index) {
        if (startTags.containsKey(index)) {
            List<String> startStyles = startTags.get(index);
            for (final String startStyle : startStyles) {
                result.append(startStyle);
            }
        }
    }
}
