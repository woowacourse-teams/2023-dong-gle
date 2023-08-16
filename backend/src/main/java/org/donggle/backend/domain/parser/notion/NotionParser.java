package org.donggle.backend.domain.parser.notion;

import org.donggle.backend.application.service.vendor.notion.dto.NotionBlockNode;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.block.Block;
import org.donggle.backend.domain.writing.block.CodeBlock;
import org.donggle.backend.domain.writing.block.Depth;
import org.donggle.backend.domain.writing.block.HorizontalRulesBlock;
import org.donggle.backend.domain.writing.block.ImageBlock;
import org.donggle.backend.domain.writing.block.ImageCaption;
import org.donggle.backend.domain.writing.block.ImageUrl;
import org.donggle.backend.domain.writing.block.Language;
import org.donggle.backend.domain.writing.block.NormalBlock;
import org.donggle.backend.domain.writing.block.RawText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class NotionParser {
    private final Map<NotionBlockType, Function<NotionBlockNode, Optional<Block>>> NOTION_BLOCK_TYPE_MAP = new HashMap<>();
    private final Writing writing;

    public NotionParser(final Writing writing) {
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.BOOKMARK, notionBlockNode -> createNormalBlock(notionBlockNode, BookmarkParser.from(notionBlockNode), BlockType.PARAGRAPH));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.CALLOUT, notionBlockNode -> createNormalBlock(notionBlockNode, CalloutParser.from(notionBlockNode), BlockType.BLOCKQUOTE));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.CODE, notionBlockNode -> createCodeBlock(CodeBlockParser.from(notionBlockNode)));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.HEADING_1, notionBlockNode -> createNormalBlock(notionBlockNode, HeadingParser.from(notionBlockNode), BlockType.HEADING1));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.HEADING_2, notionBlockNode -> createNormalBlock(notionBlockNode, HeadingParser.from(notionBlockNode), BlockType.HEADING2));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.HEADING_3, notionBlockNode -> createNormalBlock(notionBlockNode, HeadingParser.from(notionBlockNode), BlockType.HEADING3));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.BULLETED_LIST_ITEM, notionBlockNode -> createNormalBlock(notionBlockNode, DefaultBlockParser.from(notionBlockNode), BlockType.UNORDERED_LIST));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.NUMBERED_LIST_ITEM, notionBlockNode -> createNormalBlock(notionBlockNode, DefaultBlockParser.from(notionBlockNode), BlockType.ORDERED_LIST));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.PARAGRAPH, notionBlockNode -> createNormalBlock(notionBlockNode, DefaultBlockParser.from(notionBlockNode), BlockType.PARAGRAPH));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.QUOTE, notionBlockNode -> createNormalBlock(notionBlockNode, DefaultBlockParser.from(notionBlockNode), BlockType.BLOCKQUOTE));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.TO_DO, notionBlockNode -> createNormalBlock(notionBlockNode, DefaultBlockParser.from(notionBlockNode), BlockType.PARAGRAPH));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.TOGGLE, notionBlockNode -> createNormalBlock(notionBlockNode, DefaultBlockParser.from(notionBlockNode), BlockType.PARAGRAPH));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.IMAGE, notionBlockNode -> createImageBlock(ImageParser.from(notionBlockNode)));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.DIVIDER, notionBlockNode -> createHorizontalBlock());
        this.writing = writing;
    }

    private Optional<Block> createNormalBlock(final NotionBlockNode notionBlockNode, final NotionNormalBlockParser blockParser, final BlockType blockType) {
        return Optional.of(new NormalBlock(writing, Depth.from(notionBlockNode.depth()), blockType, RawText.from(blockParser.parseRawText()), blockParser.parseStyles()));
    }

    private Optional<Block> createCodeBlock(final CodeBlockParser blockParser) {
        return Optional.of(new CodeBlock(writing, BlockType.CODE_BLOCK, RawText.from(blockParser.parseRawText()), Language.from(blockParser.language())));
    }

    private Optional<Block> createImageBlock(final ImageParser blockParser) {
        return Optional.of(new ImageBlock(writing, BlockType.IMAGE, new ImageUrl(blockParser.url()), new ImageCaption(blockParser.parseCaption())));
    }

    private Optional<Block> createHorizontalBlock() {
        return Optional.of(new HorizontalRulesBlock(writing, BlockType.HORIZONTAL_RULES, RawText.from("---")));
    }

    public List<Block> parseBody(final List<NotionBlockNode> notionBlockNodes) {
        return notionBlockNodes.stream()
                .map(this::createContentFromBlockNode)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Optional<Block> createContentFromBlockNode(final NotionBlockNode notionBlockNode) {
        return NOTION_BLOCK_TYPE_MAP
                .getOrDefault(notionBlockNode.getBlockType(), unused -> Optional.empty())
                .apply(notionBlockNode);
    }
}
