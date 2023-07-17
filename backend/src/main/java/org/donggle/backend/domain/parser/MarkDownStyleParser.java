package org.donggle.backend.domain.parser;

import org.donggle.backend.domain.StyleType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkDownStyleParser {

    public String removeAllStyles(String textBlock) {
        StringBuilder buffer = new StringBuilder(textBlock);

        for (StyleType type : StyleType.values()) {
            Pattern pattern = type.getPattern();
            Matcher matcher = pattern.matcher(buffer.toString());

            StringBuffer replacementBuffer = new StringBuffer();

            while (matcher.find()) {
                if (matcher.groupCount() >= 2) {
                    String extractedText = matcher.group(2);
                    if (!extractedText.isEmpty()) { // 그룹이 비어있지 않은 경우에만 대체
                        matcher.appendReplacement(replacementBuffer, Matcher.quoteReplacement(extractedText));
                    }
                }
            }

            matcher.appendTail(replacementBuffer);

            buffer = new StringBuilder(replacementBuffer);
            replacementBuffer.setLength(0); //버퍼 비우기
        }

        return buffer.toString();
    }


    private String removeUnmatchedStyle(String textBlock, StyleType styleType) {
        StringBuilder buffer = new StringBuilder(textBlock);

        for (StyleType type : StyleType.values()) {
            if (type != styleType) {
                Pattern pattern = type.getPattern();
                Matcher matcher = pattern.matcher(buffer.toString());

                StringBuffer replacementBuffer = new StringBuffer();

                while (matcher.find()) {
                    if (matcher.groupCount() >= 2) {
                        String extractedText = matcher.group(2);
                        if (!extractedText.isEmpty()) { // 그룹이 비어있지 않은 경우에만 대체
                            matcher.appendReplacement(replacementBuffer, Matcher.quoteReplacement(extractedText));
                        }
                    }
                }

                matcher.appendTail(replacementBuffer);

                buffer = new StringBuilder(replacementBuffer);
                replacementBuffer.setLength(0); //버퍼 비우기
            }
        }

        return buffer.toString();
    }

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
