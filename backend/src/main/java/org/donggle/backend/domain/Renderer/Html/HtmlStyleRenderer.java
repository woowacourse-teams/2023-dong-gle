package org.donggle.backend.domain.Renderer.Html;

import org.donggle.backend.domain.Style;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HtmlStyleRenderer {
    public String render(final String rawText, final List<Style> styles) {
        StringBuilder result = new StringBuilder();

        Map<Integer, List<String>> startTags = new LinkedHashMap<>();
        Map<Integer, List<String>> endTags = new LinkedHashMap<>();

        createTags(styles, startTags, endTags);

        for (int i = 0; i < rawText.length(); i++) {
            if (startTags.containsKey(i)) {
                List<String> startStyles = startTags.get(i);
                for (final String startStyle : startStyles) {
                    result.append(startStyle);
                }
            }
            result.append(rawText.charAt(i));
            if (endTags.containsKey(i)) {
                List<String> endStyles = endTags.get(i);
                for (final String endStyle : endStyles) {
                    result.append(endStyle);
                }
            }
        }
        return String.valueOf(result);
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
}
