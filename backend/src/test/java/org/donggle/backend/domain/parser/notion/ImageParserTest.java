package org.donggle.backend.domain.parser.notion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ImageParserTest {
    @Test
    @DisplayName("ImageParser로부터 url과 caption을 파싱한다.")
    void parse() {
        //given
        final ImageParser imageParser = new ImageParser(List.of(
                new RichText("caption", "null", Annotations.empty())
        ), "a.com");
        //when
        final String caption = imageParser.parseCaption();

        //then
        final String expected = "caption";
        Assertions.assertAll(
                () -> assertThat(caption).isEqualTo(expected),
                () -> assertThat(imageParser.url()).isEqualTo("a.com")
        );
    }
}