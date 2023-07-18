package org.donggle.backend.domain.parser;

import org.assertj.core.api.Assertions;
import org.donggle.backend.domain.BlockType;
import org.donggle.backend.domain.Style;
import org.donggle.backend.domain.StyleType;
import org.donggle.backend.domain.content.CodeBlockContent;
import org.donggle.backend.domain.content.Content;
import org.donggle.backend.domain.content.NormalContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MarkDownParserTest {

    private MarkDownParser markDownParser;

    @BeforeEach
    void setUp() {
        markDownParser = new MarkDownParser(new MarkDownStyleParser());
    }

    @Nested
    @DisplayName("입력값의 따른 Content 구하는 테스트")
    class CreateContent {
        @Test
        @DisplayName("## 안녕하세요 content구하는 테스트")
        void createNormalContentFromTextBlock1() {
            //given
            final NormalContent content = new NormalContent(0, BlockType.HEADING2, "안녕하세요", Collections.emptyList());

            //when
            final Content result = markDownParser.createContentFromTextBlock("## 안녕하세요");

            //then
            assertThat(result).usingRecursiveComparison().isEqualTo(content);
        }

        @Test
        @DisplayName("**안`녕*하세요` 여러*분** content구하는 테스트")
        void createNormalContentFromTextBlock2() {
            //given
            final NormalContent content = new NormalContent(0, BlockType.PARAGRAPH, "안녕하세요 여러분", List.of(new Style(0, 8, StyleType.BOLD), new Style(2, 7, StyleType.ITALIC), new Style(1, 4, StyleType.CODE)));

            //when
            final Content result = markDownParser.createContentFromTextBlock("**안`녕*하세요` 여러*분**");

            //then
            assertThat(result).usingRecursiveComparison().isEqualTo(content);
        }

        @Test
        @DisplayName("안녕하**세요 여**러분 content구하는 테스트")
        void createCodeBlockContentFromTextBlock() {
            //given
            final CodeBlockContent content = new CodeBlockContent(0, BlockType.CODE_BLOCK, "나는 자바다", "java");

            //when
            final Content result = markDownParser.createContentFromTextBlock("""
                    ```java
                    나는 자바다
                    ```
                    """);

            //then
            assertThat(result).usingRecursiveComparison().isEqualTo(content);
        }

        @Test
        @DisplayName("asd")
        void asd() {
            //given
            final String text = """
                    ###안녕
                      안녕
                    ```java
                    나는 자바다
                    ```
                    ###안녕
                      안녕
                      ###안녕
                      안녕
                    """;

            //when
            final List<String> textBlocks = markDownParser.splitBlocks(text);

            //then
            Assertions.assertThat(textBlocks).hasSize(7);
        }
    }
}
