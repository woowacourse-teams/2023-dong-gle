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
}
