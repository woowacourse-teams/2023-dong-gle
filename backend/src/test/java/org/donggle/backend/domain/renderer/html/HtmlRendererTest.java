package org.donggle.backend.domain.renderer.html;

import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberName;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.block.Block;
import org.donggle.backend.domain.writing.block.CodeBlock;
import org.donggle.backend.domain.writing.block.Depth;
import org.donggle.backend.domain.writing.block.HorizontalRulesBlock;
import org.donggle.backend.domain.writing.block.Language;
import org.donggle.backend.domain.writing.block.NormalBlock;
import org.donggle.backend.domain.writing.block.RawText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HtmlRendererTest {
    private HtmlRenderer htmlRenderer;
    private List<Block> blocks;

    private Writing writing;

    @BeforeEach
    void setUp() {
        htmlRenderer = new HtmlRenderer(new HtmlStyleRenderer());
        blocks = new ArrayList<>();
        final Member member = Member.createByKakao(new MemberName("동그리"), 1L);
        final Category category = Category.basic(member);
        writing = Writing.lastOf(member, new Title("title"), category);
    }

    @Test
    @DisplayName("전체 Block 렌더링")
    void render() {
        //given
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.UNORDERED_LIST, RawText.from("1번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.UNORDERED_LIST, RawText.from("2번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.UNORDERED_LIST, RawText.from("3번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.UNORDERED_LIST, RawText.from("3-1번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.UNORDERED_LIST, RawText.from("3-2번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.ORDERED_LIST, RawText.from("3-3번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.ORDERED_LIST, RawText.from("3-4번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.UNORDERED_LIST, RawText.from("4번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.UNORDERED_LIST, RawText.from("5번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.ORDERED_LIST, RawText.from("5-1번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.UNORDERED_LIST, RawText.from("6번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.HEADING1, RawText.from("heading1"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.HEADING2, RawText.from("heading2"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.HEADING3, RawText.from("heading3"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.HEADING4, RawText.from("heading4"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.HEADING5, RawText.from("heading5"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.HEADING6, RawText.from("heading6"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.BLOCKQUOTE, RawText.from("blockquote"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.PARAGRAPH, RawText.from("paragraph"), new ArrayList<>()));
        blocks.add(new CodeBlock(writing, BlockType.CODE_BLOCK, RawText.from("public void(){}"), Language.from("java")));
        blocks.add(new CodeBlock(writing, BlockType.CODE_BLOCK, RawText.from("<button>\n    <p>\"hihi&\"</p>\n</button>"), Language.from("java")));
        blocks.add(new HorizontalRulesBlock(writing, BlockType.HORIZONTAL_RULES, RawText.from("***")));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.UNCHECKED_TASK_LIST, RawText.from("uncheckedTaskList"), List.of()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.CHECKED_TASK_LIST, RawText.from("checkedTaskList"), List.of()));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<ul><li>1번줄</li><li>2번줄</li><li>3번줄</li><ul><li>3-1번줄</li><li>3-2번줄</li></ul><ol><li>3-3번줄</li><li>3-4번줄</li></ol><li>4번줄</li><li>5번줄</li><ol><li>5-1번줄</li></ol><li>6번줄</li></ul><h1>heading1</h1><h2>heading2</h2><h3>heading3</h3><h4>heading4</h4><h5>heading5</h5><h6>heading6</h6><blockquote>blockquote</blockquote><p>paragraph</p><pre><code class=\"language-java\">public void(){}</code></pre><pre><code class=\"language-java\">&lt;button&gt;&NewLine;&Tab;&lt;p&gt;&quot;hihi&amp;&quot;&lt;/p&gt;&NewLine;&lt;/button&gt;</code></pre><hr></hr><div><input type=\"checkbox\" unchecked>uncheckedTaskList</input></div><div><input type=\"checkbox\" checked>checkedTaskList</input></div>";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Heading1 렌더링")
    void renderHeading1() {
        //given
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.HEADING1, RawText.from("Heading1"), new ArrayList<>()));

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
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.HEADING2, RawText.from("Heading2"), new ArrayList<>()));

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
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.HEADING3, RawText.from("Heading3"), new ArrayList<>()));

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
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.HEADING4, RawText.from("Heading4"), new ArrayList<>()));

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
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.HEADING5, RawText.from("Heading5"), new ArrayList<>()));

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
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.HEADING6, RawText.from("Heading6"), new ArrayList<>()));

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
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.BLOCKQUOTE, RawText.from("blockquote"), new ArrayList<>()));

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
        blocks.add(new CodeBlock(writing, BlockType.CODE_BLOCK, RawText.from("public void(){}"), Language.from("java")));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<pre><code class=\"language-java\">public void(){}</code></pre>";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("CodeBlock escape 렌더링")
    void renderCodeBlock2() {
        //given
        blocks.add(new CodeBlock(writing, BlockType.CODE_BLOCK, RawText.from("<button>\n    <p>\"hihi&\"</p>\n</button>"), Language.from("java")));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<pre><code class=\"language-java\">&lt;button&gt;&NewLine;&Tab;&lt;p&gt;&quot;hihi&amp;&quot;&lt;/p&gt;&NewLine;&lt;/button&gt;</code></pre>";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("리스트 렌더링")
    void renderList() {
        //given
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.UNORDERED_LIST, RawText.from("1번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.UNORDERED_LIST, RawText.from("2번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.UNORDERED_LIST, RawText.from("3번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.UNORDERED_LIST, RawText.from("3-1번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.UNORDERED_LIST, RawText.from("3-2번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.ORDERED_LIST, RawText.from("3-3번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.ORDERED_LIST, RawText.from("3-4번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.UNORDERED_LIST, RawText.from("4번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.UNORDERED_LIST, RawText.from("5번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.ORDERED_LIST, RawText.from("5-1번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.UNORDERED_LIST, RawText.from("6번줄"), new ArrayList<>()));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<ul><li>1번줄</li><li>2번줄</li><li>3번줄</li><ul><li>3-1번줄</li><li>3-2번줄</li></ul><ol><li>3-3번줄</li><li>3-4번줄</li></ol><li>4번줄</li><li>5번줄</li><ol><li>5-1번줄</li></ol><li>6번줄</li></ul>";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("HorizontalRules 렌더링")
    void renderHorizontalRules() {
        //given
        blocks.add(new HorizontalRulesBlock(writing, BlockType.HORIZONTAL_RULES, RawText.from("***")));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<hr></hr>";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("uncheckedList 렌더링")
    void renderUnChekcedTaskList() {
        //given
        blocks.add(new NormalBlock(
                writing, Depth.empty(),
                BlockType.UNCHECKED_TASK_LIST,
                RawText.from("uncheckedTaskList"),
                List.of()));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<div><input type=\"checkbox\" unchecked>uncheckedTaskList</input></div>";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("checkedList 렌더링")
    void renderChekcedTaskList() {
        //given
        blocks.add(new NormalBlock(
                writing, Depth.empty(),
                BlockType.CHECKED_TASK_LIST,
                RawText.from("checkedTaskList"),
                List.of()));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<div><input type=\"checkbox\" checked>checkedTaskList</input></div>";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("toggle 렌더링")
    void renderToggle() {
        //given
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.TOGGLE, RawText.from("토글제목"), List.of()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.UNORDERED_LIST, RawText.from("1번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.UNORDERED_LIST, RawText.from("2번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.UNORDERED_LIST, RawText.from("3번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(2), BlockType.UNORDERED_LIST, RawText.from("3-1번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(2), BlockType.UNORDERED_LIST, RawText.from("3-2번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(2), BlockType.ORDERED_LIST, RawText.from("3-3번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(2), BlockType.ORDERED_LIST, RawText.from("3-4번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.UNORDERED_LIST, RawText.from("4번줄"), new ArrayList<>()));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<details><summary>토글제목</summary><ul><li>1번줄</li><li>2번줄</li><li>3번줄</li><ul><li>3-1번줄</li><li>3-2번줄</li></ul><ol><li>3-3번줄</li><li>3-4번줄</li></ol><li>4번줄</li></ul></details>";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("toggle 렌더링2")
    void renderToggle2() {
        //given
        blocks.add(new NormalBlock(writing, Depth.empty(), BlockType.TOGGLE, RawText.from("토글제목"), List.of()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.UNORDERED_LIST, RawText.from("5번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.ORDERED_LIST, RawText.from("5-1번줄"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.HEADING2, RawText.from("heading2"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.HEADING3, RawText.from("heading3"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.HEADING4, RawText.from("heading4"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.HEADING5, RawText.from("heading5"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.HEADING6, RawText.from("heading6"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.BLOCKQUOTE, RawText.from("blockquote"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.PARAGRAPH, RawText.from("paragraph"), new ArrayList<>()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.HEADING1, RawText.from("heading1"), new ArrayList<>()));
        blocks.add(new CodeBlock(writing, Depth.from(1), BlockType.CODE_BLOCK, RawText.from("public void(){}"), Language.from("java")));
        blocks.add(new CodeBlock(writing, Depth.from(1), BlockType.CODE_BLOCK, RawText.from("<button>\n    <p>\"hihi&\"</p>\n</button>"), Language.from("java")));
        blocks.add(new HorizontalRulesBlock(writing, Depth.from(1), BlockType.HORIZONTAL_RULES, RawText.from("***")));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.UNCHECKED_TASK_LIST, RawText.from("uncheckedTaskList"), List.of()));
        blocks.add(new NormalBlock(writing, Depth.from(1), BlockType.CHECKED_TASK_LIST, RawText.from("checkedTaskList"), List.of()));

        //when
        final String result = htmlRenderer.render(blocks);
        final String expected = "<details><summary>토글제목</summary><ul><li>5번줄</li></ul><ol><li>5-1번줄</li></ol><h2>&emsp;heading2</h2><h3>&emsp;heading3</h3><h4>&emsp;heading4</h4><h5>&emsp;heading5</h5><h6>&emsp;heading6</h6><blockquote>&emsp;blockquote</blockquote><p>&emsp;paragraph</p><h1>&emsp;heading1</h1><pre><code class=\"language-java\">public void(){}</code></pre><pre><code class=\"language-java\">&lt;button&gt;&NewLine;&Tab;&lt;p&gt;&quot;hihi&amp;&quot;&lt;/p&gt;&NewLine;&lt;/button&gt;</code></pre><hr></hr><div><input type=\"checkbox\" unchecked>&emsp;uncheckedTaskList</input></div><div><input type=\"checkbox\" checked>&emsp;checkedTaskList</input></div></details>";

        //then
        assertThat(result).isEqualTo(expected);
    }
}
