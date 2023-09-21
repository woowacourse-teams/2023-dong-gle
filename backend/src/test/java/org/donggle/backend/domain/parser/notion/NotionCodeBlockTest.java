package org.donggle.backend.domain.parser.notion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class NotionCodeBlockTest {
    @Test
    @DisplayName("CodeBlockParser로부터 rawText를 파싱한다.")
    void parseRawText() {
        //given
        final NotionCodeBlock notionCodeBlock = new NotionCodeBlock(List.of(
                new RichText("void parseRawText()", "null", Annotations.empty())),
                "java"
        );
        //when
        final String rawText = notionCodeBlock.parseRawText();
        final String language = notionCodeBlock.language();

        //then
        final String expected = "void parseRawText()";
        assertAll(
                () -> assertThat(rawText).isEqualTo(expected),
                () -> assertThat(language).isEqualTo("java")
        );
    }
}