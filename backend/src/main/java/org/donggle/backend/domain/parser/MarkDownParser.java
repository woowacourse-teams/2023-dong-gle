package org.donggle.backend.domain.parser;

import org.donggle.backend.domain.BlockType;
import org.donggle.backend.domain.content.CodeBlockContent;
import org.donggle.backend.domain.content.Content;
import org.donggle.backend.domain.content.NormalContent;

import java.util.Collections;
import java.util.regex.Matcher;

public class MarkDownParser {
    private final MarkDownStyleParser markDownStyleParser;

    public MarkDownParser(final MarkDownStyleParser markDownStyleParser) {
        this.markDownStyleParser = markDownStyleParser;
    }

    public Content createContentFromTextBlock(final String textBlock) {
        for (BlockType blockType : BlockType.values()) {
            Matcher matcher = blockType.getPattern().matcher(textBlock);
            if (matcher.find()) {
                if (blockType == BlockType.CODE_BLOCK) {
                    return new CodeBlockContent(0, blockType, matcher.group(2), matcher.group(1));
                }
                return new NormalContent(0, blockType, markDownStyleParser.removeAllStyles(matcher.replaceAll("")), markDownStyleParser.extractStyles(textBlock, markDownStyleParser.removeAllStyles(textBlock)));
            }
        }
        return new NormalContent(0, BlockType.PARAGRAPH, textBlock, Collections.emptyList());
    }
}
