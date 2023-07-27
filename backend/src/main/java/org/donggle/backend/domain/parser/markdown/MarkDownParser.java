package org.donggle.backend.domain.parser.markdown;

import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.content.CodeBlockContent;
import org.donggle.backend.domain.writing.content.Content;
import org.donggle.backend.domain.writing.content.ImageContent;
import org.donggle.backend.domain.writing.content.NormalContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkDownParser {
    private static final String BLOCK_DELIMITER = "(?s)(```.*?```).*?|(.*?)(?=```|\\z)";

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
            final String codeBlock = matcher.group(1);
            final String normalBlock = matcher.group(2);

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

        switch (blockType) {
            case CODE_BLOCK -> {
                return new CodeBlockContent(0, blockType, matcher.group(2), matcher.group(1));
            }
            case IMAGE -> {
                // TODO: image regex 이전 plainText가 들어오는 경우 처리 로직 추가하기
                return new ImageContent(0, blockType, matcher.group(2), matcher.group(1));
            }
            default -> {
                final String removedBlockTypeText = matcher.replaceAll("");
                final String removedStyleTypeText = markDownStyleParser.removeStyles(removedBlockTypeText);
                final List<Style> styles = markDownStyleParser.extractStyles(removedBlockTypeText, removedStyleTypeText);
                return new NormalContent(0, blockType, removedStyleTypeText, styles);
            }
        }
    }

    private Matcher findBlockMatcher(final String textBlock) {
        return Arrays.stream(BlockType.values())
                .map(blockType -> blockType.getPattern().matcher(textBlock))
                .filter(Matcher::find)
                .findFirst()
                .orElseThrow(UnsupportedOperationException::new);
    }
}
