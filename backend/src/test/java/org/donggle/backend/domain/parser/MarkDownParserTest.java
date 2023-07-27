package org.donggle.backend.domain.parser;

import org.donggle.backend.domain.parser.markdown.MarkDownParser;
import org.donggle.backend.domain.parser.markdown.MarkDownStyleParser;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.content.CodeBlockContent;
import org.donggle.backend.domain.writing.content.Content;
import org.donggle.backend.domain.writing.content.ImageContent;
import org.donggle.backend.domain.writing.content.NormalContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MarkDownParserTest {
    private MarkDownParser markDownParser;

    @BeforeEach
    void setUp() {
        markDownParser = new MarkDownParser(new MarkDownStyleParser());
    }

    @Test
    @DisplayName("""
            ###안녕
              안녕
            ```java
            나는 자바다
            ```
            의 Contents를 정상적으로 구하는 테스트""")
    void createNormalContentFromTextBlock1() {
        //given
        final String text = """
                ###안녕
                  안녕
                ```java
                나는 자바다
                ```
                """;

        final List<Content> result = List.of(
                new NormalContent(0, BlockType.PARAGRAPH, "###안녕", Collections.emptyList()),
                new NormalContent(0, BlockType.PARAGRAPH, "  안녕", Collections.emptyList()),
                new CodeBlockContent(0, BlockType.CODE_BLOCK, "나는 자바다", "java")
        );

        //when
        final List<Content> parse = markDownParser.parse(text);

        //then
        assertThat(parse).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    @DisplayName("이미지 파싱 테스트")
    void parserImage() {
        //given
        final String text = "![imageName](www.naver.com)";
        final ImageContent expected = new ImageContent(0, BlockType.IMAGE, "www.naver.com", "imageName");

        //when
        final List<Content> result = markDownParser.parse(text);
        final ImageContent resultContent = (ImageContent) result.get(0);

        //then
        assertAll(
                () -> assertThat(resultContent.getDepth()).isEqualTo(expected.getDepth()),
                () -> assertThat(resultContent.getUrl()).isEqualTo(expected.getUrl()),
                () -> assertThat(resultContent.getCaption()).isEqualTo(expected.getCaption())
        );
    }

    @Nested
    @DisplayName("depth 파싱을 테스트한다.")
    class ParseDepth {
        @Test
        @DisplayName("공백 4개")
        void parseDepth() {
            //given
            final String text = "    - hello world\n        - hubcreator";
            final NormalContent expected1 = new NormalContent(1, BlockType.UNORDERED_LIST, "hello world", List.of());
            final NormalContent expected2 = new NormalContent(2, BlockType.UNORDERED_LIST, "hubcreator", List.of());

            //when
            final List<Content> result = markDownParser.parse(text);

            //then
            assertAll(
                    () -> assertThat(result.get(0).getDepth()).isEqualTo(expected1.getDepth()),
                    () -> assertThat(result.get(1).getDepth()).isEqualTo(expected2.getDepth())
            );
        }

        @Test
        @DisplayName("탭")
        void parseDepth2() {
            //given
            final String text = "\t- hello world\n\t\t - hubcreator";
            final NormalContent expected1 = new NormalContent(1, BlockType.UNORDERED_LIST, "hello world", List.of());
            final NormalContent expected2 = new NormalContent(2, BlockType.UNORDERED_LIST, "hubcreator", List.of());

            //when
            final List<Content> result = markDownParser.parse(text);

            //then
            assertAll(
                    () -> assertThat(result.get(0).getDepth()).isEqualTo(expected1.getDepth()),
                    () -> assertThat(result.get(1).getDepth()).isEqualTo(expected2.getDepth())
            );
        }

        @Test
        @DisplayName("공백 4개와 탭이 섞여있는 경우")
        void parseDepthWithListMixed() {
            //given
            final String text = "- depth1\n\t- depth2\n    \t- depth3\n    \t    - depth4";
            final NormalContent expected1 = new NormalContent(0, BlockType.UNORDERED_LIST, "depth1", List.of());
            final NormalContent expected2 = new NormalContent(1, BlockType.UNORDERED_LIST, "depth2", List.of());
            final NormalContent expected3 = new NormalContent(2, BlockType.UNORDERED_LIST, "depth3", List.of());
            final NormalContent expected4 = new NormalContent(3, BlockType.UNORDERED_LIST, "depth4", List.of());

            //when
            final List<Content> result = markDownParser.parse(text);

            //then
            assertAll(
                    () -> assertThat(result.get(0).getDepth()).isEqualTo(expected1.getDepth()),
                    () -> assertThat(result.get(1).getDepth()).isEqualTo(expected2.getDepth()),
                    () -> assertThat(result.get(2).getDepth()).isEqualTo(expected3.getDepth()),
                    () -> assertThat(result.get(3).getDepth()).isEqualTo(expected4.getDepth())
            );
        }

        @Test
        @DisplayName("공백 4개와 탭이 섞여있고, 중간에 공백 4개가 있는 경우")
        void parseDepthWithListMixed2() {
            //given
            final String text = "- depth1    hel\tlo\n";
            final NormalContent expected1 = new NormalContent(0, BlockType.UNORDERED_LIST, "depth1    hel\tlo", List.of());

            //when
            final List<Content> result = markDownParser.parse(text);
            final NormalContent resultContent = (NormalContent) result.get(0);

            //then
            assertAll(
                    () -> assertThat(resultContent.getDepth()).isEqualTo(expected1.getDepth()),
                    () -> assertThat(resultContent.getRawText()).isEqualTo(expected1.getRawText())
            );
        }
    }
}
