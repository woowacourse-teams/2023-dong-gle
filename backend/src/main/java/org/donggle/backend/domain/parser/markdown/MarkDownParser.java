package org.donggle.backend.domain.parser.markdown;

import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.content.CodeBlockContent;
import org.donggle.backend.domain.writing.content.Content;
import org.donggle.backend.domain.writing.content.NormalContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkDownParser {
    private static final String BLOCK_DELIMITER = "(?s)(```.*?```).*?|(.*?)(?=```|\\z)";
    private static final int RAW_TEXT_NUMBER = 2;
    private static final int LANGUAGE_NUMBER = 1;
    private static final int CODE_NUMBER = 1;
    private static final int NORMAL_NUMBER = 2;

    private final MarkDownStyleParser markDownStyleParser;

    public MarkDownParser(final MarkDownStyleParser markDownStyleParser) {
        this.markDownStyleParser = markDownStyleParser;
    }

    public List<Content> parse(final String text) {
        return splitBlocks(text).stream()
                .map(this::createContentFromTextBlock)
                .toList();
    }

    private List<String> splitBlocks(final String text) {
        final Pattern pattern = Pattern.compile(BLOCK_DELIMITER);
        final Matcher matcher = pattern.matcher(text);

        final List<String> textBlocks = new ArrayList<>();

        while (matcher.find()) {
            final String codeBlock = matcher.group(CODE_NUMBER);
            final String normalBlock = matcher.group(NORMAL_NUMBER);

            if (isExist(codeBlock)) {
                textBlocks.add(codeBlock.trim());
            }
            if (isExist(normalBlock)) {
                textBlocks.addAll(Arrays.stream(normalBlock.split("\\n"))
                        .filter(block -> !block.isEmpty())
                        .toList());
            }
        }

        return textBlocks;
    }

    private boolean isExist(final String matchText) {
        return matchText != null && !matchText.isEmpty();
    }

    private Content createContentFromTextBlock(final String textBlock) {
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
