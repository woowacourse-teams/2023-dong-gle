package org.donggle.backend.domain.renderer.html;

import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.writing.Block;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.content.CodeBlockContent;
import org.donggle.backend.domain.writing.content.NormalContent;
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
        member = new Member("동글이");
    }

    @Test
    @DisplayName("전체 Block 렌더링")
    void render() {
        //given
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.UNORDERED_LIST, "1번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.UNORDERED_LIST, "2번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.UNORDERED_LIST, "3번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(1, BlockType.UNORDERED_LIST, "3-1번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(1, BlockType.UNORDERED_LIST, "3-2번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(1, BlockType.ORDERED_LIST, "3-3번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(1, BlockType.ORDERED_LIST, "3-4번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.UNORDERED_LIST, "4번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.UNORDERED_LIST, "5번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(1, BlockType.ORDERED_LIST, "5-1번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.UNORDERED_LIST, "6번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.HEADING1, "heading1", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.HEADING2, "heading2", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.HEADING3, "heading3", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.HEADING4, "heading4", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.HEADING5, "heading5", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.HEADING6, "heading6", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.BLOCKQUOTE, "blockquote", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.PARAGRAPH, "paragraph", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new CodeBlockContent(0, BlockType.CODE_BLOCK, "public void(){}", "java")));

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
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.HEADING1, "Heading1", new ArrayList<>())));

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
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.HEADING2, "Heading2", new ArrayList<>())));

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
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.HEADING3, "Heading3", new ArrayList<>())));

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
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.HEADING4, "Heading4", new ArrayList<>())));

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
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.HEADING5, "Heading5", new ArrayList<>())));

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
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.HEADING6, "Heading6", new ArrayList<>())));

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
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.BLOCKQUOTE, "blockquote", new ArrayList<>())));

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
        blocks.add(new Block(new Writing(member, "title"), new CodeBlockContent(0, BlockType.CODE_BLOCK, "public void(){}", "java")));

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
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.UNORDERED_LIST, "1번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.UNORDERED_LIST, "2번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.UNORDERED_LIST, "3번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(1, BlockType.UNORDERED_LIST, "3-1번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(1, BlockType.UNORDERED_LIST, "3-2번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(1, BlockType.ORDERED_LIST, "3-3번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(1, BlockType.ORDERED_LIST, "3-4번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.UNORDERED_LIST, "4번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.UNORDERED_LIST, "5번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(1, BlockType.ORDERED_LIST, "5-1번줄", new ArrayList<>())));
        blocks.add(new Block(new Writing(member, "title"), new NormalContent(0, BlockType.UNORDERED_LIST, "6번줄", new ArrayList<>())));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<ul><li>1번줄</li><li>2번줄</li><li>3번줄</li><ul><li>3-1번줄</li><li>3-2번줄</li></ul><ol><li>3-3번줄</li><li>3-4번줄</li></ol><li>4번줄</li><li>5번줄</li><ol><li>5-1번줄</li></ol><li>6번줄</li></ul>";

        //then
        assertThat(result).isEqualTo(expected);
    }
}
