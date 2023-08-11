package org.donggle.backend.domain.parser.notion;

import org.donggle.backend.application.service.vendor.notion.dto.NotionBlockNode;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.content.Block;
import org.donggle.backend.domain.writing.content.CodeBlock;
import org.donggle.backend.domain.writing.content.Depth;
import org.donggle.backend.domain.writing.content.ImageBlock;
import org.donggle.backend.domain.writing.content.ImageCaption;
import org.donggle.backend.domain.writing.content.ImageUrl;
import org.donggle.backend.domain.writing.content.Language;
import org.donggle.backend.domain.writing.content.NormalBlock;
import org.donggle.backend.domain.writing.content.RawText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class NotionParser {
    private static final Map<NotionBlockType, Function<NotionBlockNode, Optional<Block>>> NOTION_BLOCK_TYPE_MAP = new HashMap<>();

    static {
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.BOOKMARK, notionBlockNode -> createNormalContent(notionBlockNode, BookmarkParser.from(notionBlockNode), BlockType.PARAGRAPH));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.CALLOUT, notionBlockNode -> createNormalContent(notionBlockNode, CalloutParser.from(notionBlockNode), BlockType.BLOCKQUOTE));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.CODE, notionBlockNode -> createCodeBlockContent(CodeBlockParser.from(notionBlockNode)));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.HEADING_1, notionBlockNode -> createNormalContent(notionBlockNode, HeadingParser.from(notionBlockNode), BlockType.HEADING1));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.HEADING_2, notionBlockNode -> createNormalContent(notionBlockNode, HeadingParser.from(notionBlockNode), BlockType.HEADING2));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.HEADING_3, notionBlockNode -> createNormalContent(notionBlockNode, HeadingParser.from(notionBlockNode), BlockType.HEADING3));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.BULLETED_LIST_ITEM, notionBlockNode -> createNormalContent(notionBlockNode, DefaultBlockParser.from(notionBlockNode), BlockType.UNORDERED_LIST));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.NUMBERED_LIST_ITEM, notionBlockNode -> createNormalContent(notionBlockNode, DefaultBlockParser.from(notionBlockNode), BlockType.ORDERED_LIST));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.PARAGRAPH, notionBlockNode -> createNormalContent(notionBlockNode, DefaultBlockParser.from(notionBlockNode), BlockType.PARAGRAPH));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.QUOTE, notionBlockNode -> createNormalContent(notionBlockNode, DefaultBlockParser.from(notionBlockNode), BlockType.BLOCKQUOTE));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.TO_DO, notionBlockNode -> createNormalContent(notionBlockNode, DefaultBlockParser.from(notionBlockNode), BlockType.PARAGRAPH));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.TOGGLE, notionBlockNode -> createNormalContent(notionBlockNode, DefaultBlockParser.from(notionBlockNode), BlockType.PARAGRAPH));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.IMAGE, notionBlockNode -> createImageContent(ImageParser.from(notionBlockNode)));
    }

    private static Optional<Block> createNormalContent(final NotionBlockNode notionBlockNode, final NotionNormalBlockParser blockParser, final BlockType blockType) {
        return Optional.of(new NormalBlock(Depth.from(notionBlockNode.depth()), blockType, RawText.from(blockParser.parseRawText()), blockParser.parseStyles()));
    }

    private static Optional<Block> createCodeBlockContent(final CodeBlockParser blockParser) {
        return Optional.of(new CodeBlock(BlockType.CODE_BLOCK, RawText.from(blockParser.parseRawText()), Language.from(blockParser.language())));
    }

    private static Optional<Block> createImageContent(final ImageParser blockParser) {
        return Optional.of(new ImageBlock(BlockType.IMAGE, new ImageUrl(blockParser.url()), new ImageCaption(blockParser.parseCaption())));
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

    public String parseTitle(final NotionBlockNode parentBlockNode) {
        return parentBlockNode.getBlockProperties().get("title").asText();
    }
}