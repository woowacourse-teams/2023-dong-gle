package org.donggle.backend.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.regex.Pattern;

@Getter
public enum BlockType {
    HEADING1("^#{1}\\ "),
    HEADING2("^#{2}\\ "),
    HEADING3("^#{3}\\ "),
    HEADING4("^#{4}\\ "),
    HEADING5("^#{5}\\ "),
    HEADING6("^#{6}\\ "),
    BLOCKQUOTE("^>\\ "),
    UNORDERED_LIST("^-{1}\\ "),
    ORDERED_LIST("^[1-9][0-9]{0,4}\\.\\ "),
    CODE_BLOCK("^```[a-zA-Z]*\\n([\\s\\S]*?)\\n```$"),
    PARAGRAPH("");
    //TODO image type regex 넣기
    //    IMAGE(""),

    private final Pattern pattern;

    BlockType(final String content) {
        this.pattern = Pattern.compile(content);
    }

    public static BlockType of(final String content) {
        return Arrays.stream(values())
                .filter(m -> m.getPattern().matcher(content).find())
                .findFirst()
                .orElseThrow();
    }
}
