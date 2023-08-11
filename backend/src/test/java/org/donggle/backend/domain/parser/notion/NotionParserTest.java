package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.application.service.vendor.notion.dto.NotionBlockNode;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.member.Email;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberName;
import org.donggle.backend.domain.member.Password;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleRange;
import org.donggle.backend.domain.writing.StyleType;
import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.content.Block;
import org.donggle.backend.domain.writing.content.CodeBlock;
import org.donggle.backend.domain.writing.content.Depth;
import org.donggle.backend.domain.writing.content.ImageBlock;
import org.donggle.backend.domain.writing.content.ImageCaption;
import org.donggle.backend.domain.writing.content.ImageUrl;
import org.donggle.backend.domain.writing.content.Language;
import org.donggle.backend.domain.writing.content.NormalBlock;
import org.donggle.backend.domain.writing.content.RawText;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NotionParserTest {
    private Writing writing;

    @BeforeEach
    void setUp() {
        final Member member = new Member(new MemberName("동그리"), new Email("a@a.com"), new Password("1234"));
        final Category category = Category.basic(member);
        writing = Writing.lastOf(member, new Title("title"), category);
    }

    @Test
    @DisplayName("Paragraph타입 BlockNode로부터 NormalContent를 생성한다.")
    void createNormalContentFromBlockNode() {
        //given
        final JsonNode jsonNode = NotionBlockJsonBuilder.buildJsonBody("paragraph", false);
        final NotionBlockNode notionBlockNode = new NotionBlockNode(jsonNode, 0);
        final NotionParser notionParser = new NotionParser(writing);
        final List<NotionBlockNode> notionBlockNodes = List.of(new NotionBlockNode(jsonNode, 0));

        //when
        final Block contents = notionParser.parseBody(notionBlockNodes).get(0);

        //then
        final NotionNormalBlockParser blockParser = DefaultBlockParser.from(notionBlockNode);
        final String rawText = blockParser.parseRawText();
        final List<Style> styles = blockParser.parseStyles();
        final NormalBlock expected = new NormalBlock(writing, Depth.from(0), BlockType.PARAGRAPH, RawText.from(rawText), styles);
        assertThat(contents)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt", "styles")
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Code타입 BlockNode로부터 CodeBlockContent를 생성한다.")
    void createCodeBlockContentFromBlockNode() {
        //given
        final JsonNode jsonNode = NotionBlockJsonBuilder.buildJsonBody("code", false);
        final NotionBlockNode notionBlockNode = new NotionBlockNode(jsonNode, 0);
        final NotionParser notionParser = new NotionParser(writing);
        final List<NotionBlockNode> notionBlockNodes = List.of(new NotionBlockNode(jsonNode, 0));

        //when
        final Block contents = notionParser.parseBody(notionBlockNodes).get(0);

        //then
        final CodeBlockParser blockParser = CodeBlockParser.from(notionBlockNode);
        final String rawText = blockParser.parseRawText();

        assertThat(contents)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(new CodeBlock(writing, BlockType.CODE_BLOCK, RawText.from(rawText), Language.from(blockParser.language())));
    }

    @Test
    @DisplayName("Image타입 BlockNode로부터 ImageContent를 생성한다.")
    void createImageBlockContentFromBlockNode() {
        //given
        final JsonNode jsonNode = NotionBlockJsonBuilder.buildJsonBody("image", false);
        final NotionBlockNode notionBlockNode = new NotionBlockNode(jsonNode, 0);
        final NotionParser notionParser = new NotionParser(writing);
        final List<NotionBlockNode> notionBlockNodes = List.of(new NotionBlockNode(jsonNode, 0));

        //when
        final Block contents = notionParser.parseBody(notionBlockNodes).get(0);

        //then
        final ImageParser imageParser = ImageParser.from(notionBlockNode);
        final String url = imageParser.url();
        final String caption = imageParser.parseCaption();

        assertThat(contents)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(new ImageBlock(writing, BlockType.IMAGE, new ImageUrl(url), new ImageCaption(caption)));
    }

    @Test
    @DisplayName("Bookmark타입 BlockNode로부터 BookmarkContent를 생성한다.")
    void createBookmarkBlockContentFromBlockNode() {
        //given
        final JsonNode jsonNode = NotionBlockJsonBuilder.buildJsonBody("bookmark", false);
        final NotionBlockNode notionBlockNode = new NotionBlockNode(jsonNode, 0);
        final NotionParser notionParser = new NotionParser(writing);
        final List<NotionBlockNode> notionBlockNodes = List.of(new NotionBlockNode(jsonNode, 0));

        //when
        final Block contents = notionParser.parseBody(notionBlockNodes).get(0);

        //then
        final BookmarkParser bookmarkParser = BookmarkParser.from(notionBlockNode);
        final String rawText = bookmarkParser.parseRawText();
        final NormalBlock expected = new NormalBlock(
                writing,
                Depth.from(0),
                BlockType.PARAGRAPH,
                RawText.from(rawText), List.of(
                new Style(new StyleRange(0, 0), StyleType.LINK),
                new Style(new StyleRange(0, 6), StyleType.LINK)
        ));

        final NormalBlock normalContent = (NormalBlock) contents;
        final List<Style> styles = normalContent.getStyles();
        Assertions.assertAll(
                () -> assertThat(normalContent)
                        .usingRecursiveComparison()
                        .ignoringFields("id", "createdAt", "updatedAt", "styles")
                        .isEqualTo(expected),
                () -> assertThat(styles)
                        .usingRecursiveComparison()
                        .ignoringFields("id", "createdAt", "updatedAt")
                        .isEqualTo(expected.getStyles())
        );
    }
}