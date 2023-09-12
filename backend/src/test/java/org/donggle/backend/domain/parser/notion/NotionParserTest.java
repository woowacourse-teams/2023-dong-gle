package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.application.service.vendor.notion.dto.NotionBlockNode;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberName;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleRange;
import org.donggle.backend.domain.writing.StyleType;
import org.donggle.backend.domain.writing.Title;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NotionParserTest {
    private NotionParser notionParser;

    @BeforeEach
    void setUp() {
        notionParser = new NotionParser();
    }

    @Test
    @DisplayName("Paragraph타입 BlockNode로부터 NormalBlock을 생성한다.")
    void createNormalBlockFromBlockNode() {
        //given
        final JsonNode jsonNode = NotionBlockJsonBuilder.buildJsonBody("paragraph", false);
        final NotionBlockNode notionBlockNode = new NotionBlockNode(jsonNode, 0);
        final List<NotionBlockNode> notionBlockNodes = List.of(new NotionBlockNode(jsonNode, 0));

        //when
        final Block blocks = notionParser.parseBody(notionBlockNodes).get(0);

        //then
        final NotionNormalBlockParser blockParser = DefaultBlockParser.from(notionBlockNode);
        final String rawText = blockParser.parseRawText();
        final List<Style> styles = blockParser.parseStyles();
        final NormalBlock expected = new NormalBlock(Depth.from(0), BlockType.PARAGRAPH, RawText.from(rawText), styles);
        assertThat(blocks)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt", "styles")
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Code타입 BlockNode로부터 CodeBlock을 생성한다.")
    void createCodeBlockFromBlockNode() {
        //given
        final JsonNode jsonNode = NotionBlockJsonBuilder.buildJsonBody("code", false);
        final NotionBlockNode notionBlockNode = new NotionBlockNode(jsonNode, 0);
        final List<NotionBlockNode> notionBlockNodes = List.of(new NotionBlockNode(jsonNode, 0));

        //when
        final Block block = notionParser.parseBody(notionBlockNodes).get(0);

        //then
        final CodeBlockParser blockParser = CodeBlockParser.from(notionBlockNode);
        final String rawText = blockParser.parseRawText();

        assertThat(block)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(new CodeBlock(BlockType.CODE_BLOCK, RawText.from(rawText), Language.from(blockParser.language())));
    }

    @Test
    @DisplayName("Image타입 BlockNode로부터 ImageBlock을 생성한다.")
    void createImageBlockFromBlockNode() {
        //given
        final JsonNode jsonNode = NotionBlockJsonBuilder.buildJsonBody("image", false);
        final NotionBlockNode notionBlockNode = new NotionBlockNode(jsonNode, 0);
        final List<NotionBlockNode> notionBlockNodes = List.of(new NotionBlockNode(jsonNode, 0));

        //when
        final Block block = notionParser.parseBody(notionBlockNodes).get(0);

        //then
        final ImageParser imageParser = ImageParser.from(notionBlockNode);
        final String url = imageParser.url();
        final String caption = imageParser.parseCaption();

        assertThat(block)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(new ImageBlock(BlockType.IMAGE, new ImageUrl(url), new ImageCaption(caption)));
    }

    @Test
    @DisplayName("Bookmark타입 BlockNode로부터 BookmarkBlock을 생성한다.")
    void createBookmarkBlockFromBlockNode() {
        //given
        final JsonNode jsonNode = NotionBlockJsonBuilder.buildJsonBody("bookmark", false);
        final NotionBlockNode notionBlockNode = new NotionBlockNode(jsonNode, 0);
        final List<NotionBlockNode> notionBlockNodes = List.of(new NotionBlockNode(jsonNode, 0));

        //when
        final Block block = notionParser.parseBody(notionBlockNodes).get(0);

        //then
        final BookmarkParser bookmarkParser = BookmarkParser.from(notionBlockNode);
        final String rawText = bookmarkParser.parseRawText();
        final NormalBlock expected = new NormalBlock(
                Depth.from(0),
                BlockType.PARAGRAPH,
                RawText.from(rawText), List.of(
                new Style(new StyleRange(0, 0), StyleType.LINK),
                new Style(new StyleRange(0, 6), StyleType.LINK)
        ));

        final NormalBlock normalBlock = (NormalBlock) block;
        final List<Style> styles = normalBlock.getStyles();
        assertAll(
                () -> assertThat(normalBlock)
                        .usingRecursiveComparison()
                        .ignoringFields("id", "createdAt", "updatedAt", "styles")
                        .isEqualTo(expected),
                () -> assertThat(styles)
                        .usingRecursiveComparison()
                        .ignoringFields("id", "createdAt", "updatedAt")
                        .isEqualTo(expected.getStyles())
        );
    }

    @Test
    @DisplayName("Divide타입 BlockNode로부터 HorizontalRulesBlock을 생성한다.")
    void createHorizontalRulesBlockFromBlockNode() {
        //given
        final JsonNode jsonNode = NotionBlockJsonBuilder.buildJsonBody("divider", false);
        final List<NotionBlockNode> notionBlockNodes = List.of(new NotionBlockNode(jsonNode, 0));

        //when
        final Block block = notionParser.parseBody(notionBlockNodes).get(0);

        //then
        assertThat(block)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(new HorizontalRulesBlock(BlockType.HORIZONTAL_RULES, RawText.from("---")));
    }

    @Test
    @DisplayName("To-do타입 BlockNode로부터 CheckedTaskListType의 NormalBlock을 생성한다.")
    void createTaskListBLockFromBlockNode() {
        //given
        final JsonNode jsonNode = NotionBlockJsonBuilder.buildJsonBody("checked_todo", true);
        final List<NotionBlockNode> notionBlockNodes = List.of(new NotionBlockNode(jsonNode, 0));

        //when
        final Block block = notionParser.parseBody(notionBlockNodes).get(0);

        //then
        assertThat(block)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(new NormalBlock(Depth.empty(), BlockType.CHECKED_TASK_LIST, RawText.from("checked todo"), List.of()));
    }

    @Test
    @DisplayName("To-do타입 BlockNode로부터 UnCheckedTaskListType의 NormalBlock을 생성한다.")
    void createTaskListBLockFromBlockNode2() {
        //given
        final JsonNode jsonNode = NotionBlockJsonBuilder.buildJsonBody("unchecked_todo", true);
        final List<NotionBlockNode> notionBlockNodes = List.of(new NotionBlockNode(jsonNode, 0));

        //when
        final Block block = notionParser.parseBody(notionBlockNodes).get(0);

        //then
        assertThat(block)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(new NormalBlock(Depth.empty(), BlockType.UNCHECKED_TASK_LIST, RawText.from("unchecked todo"), List.of()));
    }
}
