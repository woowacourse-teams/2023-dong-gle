package org.donggle.backend.domain.parser.notion;

import org.donggle.backend.infrastructure.client.notion.dto.response.NotionBlockNodeResponse;
import org.donggle.backend.domain.writing.BlockType;
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
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
public class NotionParser {
    private final Map<NotionBlockType, Function<NotionBlockNodeResponse, Optional<Block>>> NOTION_BLOCK_TYPE_MAP = new EnumMap<>(NotionBlockType.class);

    public NotionParser() {
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.BOOKMARK, notionBlockNode -> createNormalBlock(notionBlockNode, BookmarkParser.from(notionBlockNode), BlockType.PARAGRAPH));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.CALLOUT, notionBlockNode -> createNormalBlock(notionBlockNode, CalloutParser.from(notionBlockNode), BlockType.BLOCKQUOTE));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.CODE, notionBlockNode -> createCodeBlock(notionBlockNode, CodeBlockParser.from(notionBlockNode)));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.HEADING_1, notionBlockNode -> createNormalBlock(notionBlockNode, HeadingParser.from(notionBlockNode), BlockType.HEADING1));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.HEADING_2, notionBlockNode -> createNormalBlock(notionBlockNode, HeadingParser.from(notionBlockNode), BlockType.HEADING2));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.HEADING_3, notionBlockNode -> createNormalBlock(notionBlockNode, HeadingParser.from(notionBlockNode), BlockType.HEADING3));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.BULLETED_LIST_ITEM, notionBlockNode -> createNormalBlock(notionBlockNode, DefaultBlockParser.from(notionBlockNode), BlockType.UNORDERED_LIST));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.NUMBERED_LIST_ITEM, notionBlockNode -> createNormalBlock(notionBlockNode, DefaultBlockParser.from(notionBlockNode), BlockType.ORDERED_LIST));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.PARAGRAPH, notionBlockNode -> createNormalBlock(notionBlockNode, DefaultBlockParser.from(notionBlockNode), BlockType.PARAGRAPH));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.QUOTE, notionBlockNode -> createNormalBlock(notionBlockNode, DefaultBlockParser.from(notionBlockNode), BlockType.BLOCKQUOTE));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.TO_DO, notionBlockNode -> createTaskListBLock(notionBlockNode, TodoParser.from(notionBlockNode)));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.TOGGLE, notionBlockNode -> createNormalBlock(notionBlockNode, DefaultBlockParser.from(notionBlockNode), BlockType.TOGGLE));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.IMAGE, notionBlockNode -> createImageBlock(notionBlockNode, ImageParser.from(notionBlockNode)));
        NOTION_BLOCK_TYPE_MAP.put(NotionBlockType.DIVIDER, notionBlockNode -> createHorizontalBlock(notionBlockNode));
    }

    private Optional<Block> createNormalBlock(final NotionBlockNodeResponse notionBlockNodeResponse, final NotionNormalBlockParser blockParser, final BlockType blockType) {
        return Optional.of(new NormalBlock(Depth.from(notionBlockNodeResponse.depth()), blockType, RawText.from(blockParser.parseRawText()), blockParser.parseStyles()));
    }

    private Optional<Block> createCodeBlock(final NotionBlockNodeResponse notionBlockNodeResponse, final CodeBlockParser blockParser) {
        return Optional.of(new CodeBlock(Depth.from(notionBlockNodeResponse.depth()), BlockType.CODE_BLOCK, RawText.from(blockParser.parseRawText()), Language.from(blockParser.language())));
    }

    private Optional<Block> createImageBlock(final NotionBlockNodeResponse notionBlockNodeResponse, final ImageParser blockParser) {
        return Optional.of(new ImageBlock(Depth.from(notionBlockNodeResponse.depth()), BlockType.IMAGE, new ImageUrl(blockParser.url()), new ImageCaption(blockParser.parseCaption())));
    }

    private Optional<Block> createHorizontalBlock(final NotionBlockNodeResponse notionBlockNodeResponse) {
        return Optional.of(new HorizontalRulesBlock(Depth.from(notionBlockNodeResponse.depth()), BlockType.HORIZONTAL_RULES, RawText.from("---")));
    }

    private Optional<Block> createTaskListBLock(final NotionBlockNodeResponse notionBlockNodeResponse, final TodoParser blockParser) {
        if (blockParser.checked()) {
            return Optional.of(new NormalBlock(Depth.from(notionBlockNodeResponse.depth()), BlockType.CHECKED_TASK_LIST, RawText.from(blockParser.parseRawText()), blockParser.parseStyles()));
        }
        return Optional.of(new NormalBlock(Depth.from(notionBlockNodeResponse.depth()), BlockType.UNCHECKED_TASK_LIST, RawText.from(blockParser.parseRawText()), blockParser.parseStyles()));
    }

    public List<Block> parseBody(final List<NotionBlockNodeResponse> notionBlockNodeResponses) {
        return notionBlockNodeResponses.stream()
                .map(this::createContentFromBlockNode)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Optional<Block> createContentFromBlockNode(final NotionBlockNodeResponse notionBlockNodeResponse) {
        return NOTION_BLOCK_TYPE_MAP
                .getOrDefault(notionBlockNodeResponse.getBlockType(), unused -> Optional.empty())
                .apply(notionBlockNodeResponse);
    }
}
