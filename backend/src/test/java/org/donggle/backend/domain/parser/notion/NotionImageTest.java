package org.donggle.backend.domain.parser.notion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NotionImageTest {
    @Test
    @DisplayName("ImageParser로부터 url과 caption을 파싱한다.")
    void parse() {
        //given
        final NotionImage notionImage = new NotionImage(List.of(
                new RichText("caption", "null", Annotations.empty())
        ), "a.com", FileType.EXTERNAL);
        //when
        final String caption = notionImage.parseCaption();

        //then
        final String expected = "caption";
        Assertions.assertAll(
                () -> assertThat(caption).isEqualTo(expected),
                () -> assertThat(notionImage.url()).isEqualTo("a.com")
        );
    }
}