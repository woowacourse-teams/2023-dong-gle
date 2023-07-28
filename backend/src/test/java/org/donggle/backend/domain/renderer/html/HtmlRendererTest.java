package org.donggle.backend.domain.renderer.html;

import org.donggle.backend.domain.member.Email;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberName;
import org.donggle.backend.domain.member.Password;
import org.donggle.backend.domain.writing.Block;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.content.CodeBlockContent;
import org.donggle.backend.domain.writing.content.Depth;
import org.donggle.backend.domain.writing.content.Language;
import org.donggle.backend.domain.writing.content.NormalContent;
import org.donggle.backend.domain.writing.content.RawText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HtmlRendererTest {
    private HtmlRenderer htmlRenderer;
    private List<Block> blocks;
    private Member member;

    @BeforeEach
    void setUp() {
        htmlRenderer = new HtmlRenderer(new HtmlStyleRenderer());
        blocks = new ArrayList<>();
        member = new Member(new MemberName("동그리"), new Email("a@a.com"), new Password("1234"));
    }

    @Test
    @DisplayName("전체 Block 렌더링")
    void render() {
        //given
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.UNORDERED_LIST, new RawText("1번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.UNORDERED_LIST, new RawText("2번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.UNORDERED_LIST, new RawText("3번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.from(1), BlockType.UNORDERED_LIST, new RawText("3-1번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.from(1), BlockType.UNORDERED_LIST, new RawText("3-2번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.from(1), BlockType.ORDERED_LIST, new RawText("3-3번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.from(1), BlockType.ORDERED_LIST, new RawText("3-4번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.UNORDERED_LIST, new RawText("4번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.UNORDERED_LIST, new RawText("5번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.from(1), BlockType.ORDERED_LIST, new RawText("5-1번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.UNORDERED_LIST, new RawText("6번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.HEADING1, new RawText("heading1"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.HEADING2, new RawText("heading2"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.HEADING3, new RawText("heading3"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.HEADING4, new RawText("heading4"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.HEADING5, new RawText("heading5"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.HEADING6, new RawText("heading6"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.BLOCKQUOTE, new RawText("blockquote"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.PARAGRAPH, new RawText("paragraph"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new CodeBlockContent(BlockType.CODE_BLOCK, new RawText("public void(){}"), new Language("java"))));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<ul><li>1번줄</li><li>2번줄</li><li>3번줄</li><ul><li>3-1번줄</li><li>3-2번줄</li></ul><ol><li>3-3번줄</li><li>3-4번줄</li></ol><li>4번줄</li><li>5번줄</li><ol><li>5-1번줄</li></ol><li>6번줄</li></ul><h1>heading1</h1><h2>heading2</h2><h3>heading3</h3><h4>heading4</h4><h5>heading5</h5><h6>heading6</h6><blockquote>blockquote</blockquote><p>paragraph</p><pre><code class=\"language-java\">public void(){}</code></pre>";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Heading1 렌더링")
    void renderHeading1() {
        //given
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.HEADING1, new RawText("Heading1"), new ArrayList<>())));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<h1>Heading1</h1>";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Heading2 렌더링")
    void renderHeading2() {
        //given
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.HEADING2, new RawText("Heading2"), new ArrayList<>())));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<h2>Heading2</h2>";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Heading3 렌더링")
    void renderHeading3() {
        //given
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.HEADING3, new RawText("Heading3"), new ArrayList<>())));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<h3>Heading3</h3>";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Heading4 렌더링")
    void renderHeading4() {
        //given
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.HEADING4, new RawText("Heading4"), new ArrayList<>())));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<h4>Heading4</h4>";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Heading5 렌더링")
    void renderHeading5() {
        //given
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.HEADING5, new RawText("Heading5"), new ArrayList<>())));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<h5>Heading5</h5>";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Heading6 렌더링")
    void renderHeading6() {
        //given
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.HEADING6, new RawText("Heading6"), new ArrayList<>())));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<h6>Heading6</h6>";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Blockquote 렌더링")
    void blockquote() {
        //given
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.BLOCKQUOTE, new RawText("blockquote"), new ArrayList<>())));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<blockquote>blockquote</blockquote>";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("CodeBlock 렌더링")
    void renderCodeBlock() {
        //given
        blocks.add(new Block(new Writing(member, new Title("title")), new CodeBlockContent(BlockType.CODE_BLOCK, new RawText("public void(){}"), new Language("java"))));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<pre><code class=\"language-java\">public void(){}</code></pre>";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("리스트 렌더링")
    void renderList() {
        //given
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.UNORDERED_LIST, new RawText("1번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.UNORDERED_LIST, new RawText("2번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.UNORDERED_LIST, new RawText("3번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.from(1), BlockType.UNORDERED_LIST, new RawText("3-1번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.from(1), BlockType.UNORDERED_LIST, new RawText("3-2번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.from(1), BlockType.ORDERED_LIST, new RawText("3-3번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.from(1), BlockType.ORDERED_LIST, new RawText("3-4번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.UNORDERED_LIST, new RawText("4번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.UNORDERED_LIST, new RawText("5번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.from(1), BlockType.ORDERED_LIST, new RawText("5-1번줄"), new ArrayList<>())));
        blocks.add(new Block(new Writing(member, new Title("title")), new NormalContent(Depth.empty(), BlockType.UNORDERED_LIST, new RawText("6번줄"), new ArrayList<>())));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<ul><li>1번줄</li><li>2번줄</li><li>3번줄</li><ul><li>3-1번줄</li><li>3-2번줄</li></ul><ol><li>3-3번줄</li><li>3-4번줄</li></ol><li>4번줄</li><li>5번줄</li><ol><li>5-1번줄</li></ol><li>6번줄</li></ul>";

        //then
        assertThat(result).isEqualTo(expected);
    }
}
