package org.donggle.backend.domain.parser.notion;

import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleRange;
import org.donggle.backend.domain.writing.StyleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultBlockParserTest {
    @Test
    @DisplayName("DefaultBlockParser로부터 RawText와 Styles를 파싱한다.")
    void parse() {
        //given
        final DefaultBlockParser defaultBlockParser = new DefaultBlockParser(List.of(
                new RichText("hello", "null", new Annotations(true, false, false, false, false, "default")),
                new RichText(" ", "null", Annotations.empty()),
                new RichText("world", "null", new Annotations(false, true, false, false, true, "default"))
        ));

        //when
        final String rawText = defaultBlockParser.parseRawText();
        final List<Style> styles = defaultBlockParser.parseStyles();

        //then
        final String expectedRawText = "hello world";
        final List<Style> expected = List.of(
                new Style(new StyleRange(0, 4), StyleType.BOLD),
                new Style(new StyleRange(6, 10), StyleType.ITALIC),
                new Style(new StyleRange(6, 10), StyleType.CODE)
        );
        assertThat(rawText).isEqualTo(expectedRawText);
        assertThat(styles).usingRecursiveComparison()
                .ignoringFields("updatedAt", "createdAt")
                .isEqualTo(expected);

    }

}