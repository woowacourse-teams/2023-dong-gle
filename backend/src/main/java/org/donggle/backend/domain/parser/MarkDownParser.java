package org.donggle.backend.domain.parser;

import org.donggle.backend.domain.BlockType;
import org.donggle.backend.domain.content.CodeBlockContent;
import org.donggle.backend.domain.content.Content;
import org.donggle.backend.domain.content.NormalContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkDownParser {
    public static final int RAW_TEXT_NUMBER = 2;
    public static final int LANGUAGE_NUMBER = 1;
    public static final int CODE_INDEX = 1;
    public static final int NORMAL_INDEX = 2;
    private final MarkDownStyleParser markDownStyleParser;

    public MarkDownParser(final MarkDownStyleParser markDownStyleParser) {
        this.markDownStyleParser = markDownStyleParser;
    }

    public List<String> splitBlocks(final String text) {
        final Pattern pattern = Pattern.compile("(?s)(```.*?```).*?|(.*?)(?=```|\\z)");
        final Matcher matcher = pattern.matcher(text);

        final List<String> textBlocks = new ArrayList<>();

        while (matcher.find()) {
            final String codeBlock = matcher.group(CODE_INDEX);
            final String normalBlock = matcher.group(NORMAL_INDEX);

            if (isNotEmpty(codeBlock)) {
                textBlocks.add(codeBlock.trim());
            }
            if (isNotEmpty(normalBlock)) {
                textBlocks.addAll(Arrays.stream(normalBlock.split("\\n"))
                        .filter(block -> !block.isEmpty())
                        .toList());
            }
        }

        return textBlocks;
    }

    private boolean isNotEmpty(final String matchText) {
        return matchText != null && !matchText.isEmpty();
    }

    public Content createContentFromTextBlock(final String textBlock) {
        final Matcher matcher = findBlockMatcher(textBlock);
        final BlockType blockType = BlockType.findBlockType(matcher);

        if (blockType == BlockType.CODE_BLOCK) {
            return new CodeBlockContent(0, blockType, matcher.group(RAW_TEXT_NUMBER), matcher.group(LANGUAGE_NUMBER));
        }

        final String cleanedText = matcher.replaceAll("");
        final String rawText = markDownStyleParser.removeStyles(cleanedText);
        return new NormalContent(0, blockType, rawText, markDownStyleParser.extractStyles(cleanedText, rawText));
    }

    private Matcher findBlockMatcher(final String textBlock) {
        return Arrays.stream(BlockType.values())
                .map(blockType -> blockType.getPattern().matcher(textBlock))
                .filter(Matcher::find)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("BLOCK의 속성을 찾을 수 없습니다."));
    }
}
