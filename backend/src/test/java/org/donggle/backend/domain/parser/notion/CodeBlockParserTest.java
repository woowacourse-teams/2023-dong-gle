package org.donggle.backend.domain.parser.notion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CodeBlockParserTest {
    @Test
    @DisplayName("CodeBlockParser로부터 rawText를 파싱한다.")
    void parseRawText() {
        //given
        final CodeBlockParser codeBlockParser = new CodeBlockParser(List.of(
                new RichText("void parseRawText()", "null", Annotations.empty())
        ), "java");
        //when
        final String rawText = codeBlockParser.parseRawText();
        final String language = codeBlockParser.language();

        //then
        final String expected = "void parseRawText()";
        assertThat(rawText).isEqualTo(expected);
        assertThat(language).isEqualTo("java");
    }

}