package org.donggle.backend.domain.parser;

import org.donggle.backend.domain.StyleType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkDownStyleParser {

    private String removePartStyles(String textBlock, StyleType styleType) {
        StringBuilder buffer = new StringBuilder(textBlock);

        Pattern pattern = styleType.getPattern();
        Matcher matcher = pattern.matcher(buffer);

        StringBuffer replacementBuffer = new StringBuffer();

        while (matcher.find()) {
            if (matcher.groupCount() == 2) {
                String extractedText = matcher.group(2);
                if (!extractedText.isEmpty()) { // 그룹이 비어있지 않은 경우에만 대체
                    matcher.appendReplacement(replacementBuffer, Matcher.quoteReplacement(extractedText));
                }
            }
        }
        matcher.appendTail(replacementBuffer);
        return buffer.toString();
    }
}
