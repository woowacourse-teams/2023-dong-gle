package org.donggle.backend.domain.parser.notion;

import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleRange;
import org.donggle.backend.domain.writing.StyleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NotionDefaultBlockParserTest {
    @Test
    @DisplayName("DefaultBlockParser로부터 RawText와 Styles를 파싱한다.")
    void parse() {
        //given
        final NotionDefaultBlock notionDefaultBlockParser = new NotionDefaultBlock(List.of(
                new RichText("hello", "null", new Annotations(true, false, false, false, false, "default")),
                new RichText(" ", "null", Annotations.empty()),
                new RichText("world", "null", new Annotations(false, true, false, false, true, "default"))
        ));

        //when
        final String rawText = notionDefaultBlockParser.parseRawText();
        final List<Style> styles = notionDefaultBlockParser.parseStyles();

        //then
        final String expectedRawText = "hello world";
        final List<Style> expected = List.of(
                new Style(new StyleRange(0, 4), StyleType.BOLD),
                new Style(new StyleRange(6, 10), StyleType.ITALIC),
                new Style(new StyleRange(6, 10), StyleType.CODE)
        );
        Assertions.assertAll(
                () -> assertThat(rawText).isEqualTo(expectedRawText),
                () -> assertThat(styles).usingRecursiveComparison()
                        .ignoringFields("createdAt", "updatedAt")
                        .isEqualTo(expected)
        );
    }

    @Test
    @DisplayName("DefaultBlockParser로부터 RawText와 Styles를 파싱한다. - 링크가 중간에 있는 경우")
    void parseLink() {
        //given
        final NotionDefaultBlock notionDefaultBlockParser = new NotionDefaultBlock(List.of(
                new RichText("hello", "null", new Annotations(true, false, false, false, false, "default")),
                new RichText(" ", "null", Annotations.empty()),
                new RichText("world", "href", new Annotations(false, true, false, false, true, "default"))
        ));

        //when
        final String rawText = notionDefaultBlockParser.parseRawText();
        final List<Style> styles = notionDefaultBlockParser.parseStyles();

        //then
        final String expectedRawText = "hello hrefworld";
        final List<Style> expected = List.of(
                new Style(new StyleRange(0, 4), StyleType.BOLD),
                new Style(new StyleRange(6, 9), StyleType.LINK),
                new Style(new StyleRange(10, 14), StyleType.LINK),
                new Style(new StyleRange(10, 14), StyleType.ITALIC),
                new Style(new StyleRange(10, 14), StyleType.CODE)
        );
        Assertions.assertAll(
                () -> assertThat(rawText).isEqualTo(expectedRawText),
                () -> assertThat(styles).usingRecursiveComparison()
                        .ignoringFields("createdAt", "updatedAt")
                        .isEqualTo(expected)
        );
    }
}
