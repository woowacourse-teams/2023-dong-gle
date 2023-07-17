package org.donggle.backend.domain.parser;

import org.assertj.core.api.Assertions;
import org.donggle.backend.domain.BlockType;
import org.donggle.backend.domain.Style;
import org.donggle.backend.domain.StyleType;
import org.donggle.backend.domain.content.CodeBlockContent;
import org.donggle.backend.domain.content.NormalContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class MarkDownParserTest {

    private MarkDownParser markDownParserTest;

    @BeforeEach
    void setUp() {
        markDownParserTest = new MarkDownParser(new MarkDownStyleParser());
    }

    @Nested
    @DisplayName("입력값의 따른 Content 구하는 테스트")
    class CreateContent {
        @Test
        @DisplayName("## 안녕하세요 content구하는 테스트")
        void createNormalContentFromTextBlock1() {
            //given
            final NormalContent content = new NormalContent(0, BlockType.HEADING2, "안녕하세요", Collections.emptyList());

            Assertions.assertThat(markDownParserTest.createContentFromTextBlock("## 안녕하세요")).usingRecursiveComparison().isEqualTo(content);
        }

        @Test
        @DisplayName("**안`녕*하세요` 여러*분** content구하는 테스트")
        void createNormalContentFromTextBlock2() {
            final NormalContent content = new NormalContent(
                    0,
                    BlockType.PARAGRAPH,
                    "안녕하세요 여러분",
                    List.of(
                            new Style(0, 8, StyleType.BOLD),
                            new Style(2, 7, StyleType.ITALIC),
                            new Style(1, 4, StyleType.CODE)
                    ));

            Assertions.assertThat(markDownParserTest.createContentFromTextBlock("**안`녕*하세요` 여러*분**")).usingRecursiveComparison().isEqualTo(content);
        }

        @Test
        @DisplayName("안녕하**세요 여**러분 content구하는 테스트")
        void createCodeBlockContentFromTextBlock() {
            final CodeBlockContent content = new CodeBlockContent(0, BlockType.CODE_BLOCK, "나는 자바다", "java");

            Assertions.assertThat(markDownParserTest.createContentFromTextBlock(
                    """
                            ```java
                            나는 자바다
                            ```
                            """)).usingRecursiveComparison().isEqualTo(content);
        }
    }

}
