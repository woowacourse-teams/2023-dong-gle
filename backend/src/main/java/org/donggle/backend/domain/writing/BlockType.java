package org.donggle.backend.domain.writing;

import lombok.Getter;

import java.util.Arrays;
import java.util.regex.Matcher;
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
    CHECKED_TASK_LIST("^- \\[x\\] "),
    UNCHECKED_TASK_LIST("^- \\[ \\] "),
    UNORDERED_LIST("^-{1}\\ "),
    ORDERED_LIST("^[1-9][0-9]{0,4}\\.\\ "),
    CODE_BLOCK("^```([a-zA-Z]*)\\n([\\s\\S]*?)\\n```$"),
    IMAGE("\\!\\[(.*)\\]\\((.*)\\)"),
    HORIZONTAL_RULES("^((?:\\* *){3,}|(?:- *){3,}|(?:_ *){3,})$"),
    PARAGRAPH("");

    private final Pattern pattern;

    BlockType(final String content) {
        this.pattern = Pattern.compile(content);
    }

    public static BlockType findBlockType(final Matcher matcher) {
        return Arrays.stream(BlockType.values())
                .filter(blockType -> blockType.getPattern().equals(matcher.pattern()))
                .findFirst()
                .orElse(PARAGRAPH);
    }
}
