package org.donggle.backend.domain.parser;

import org.donggle.backend.domain.parser.markdown.MarkDownParser;
import org.donggle.backend.domain.parser.markdown.MarkDownStyleParser;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.content.CodeBlockContent;
import org.donggle.backend.domain.writing.content.Content;
import org.donggle.backend.domain.writing.content.NormalContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
}
