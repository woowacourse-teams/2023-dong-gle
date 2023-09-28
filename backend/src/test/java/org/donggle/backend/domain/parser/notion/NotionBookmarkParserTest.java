package org.donggle.backend.domain.parser.notion;

import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleRange;
import org.donggle.backend.domain.writing.StyleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NotionBookmarkParserTest {
    @Test
    @DisplayName("BookmakrParser로부터 rawText와 Styles를 파싱한다.")
    void parse() {
        //given
        final NotionBookmark notionBookmarkParser = new NotionBookmark(List.of(
                new RichText("test", "null", Annotations.empty()),
                new RichText("caption", "null", Annotations.empty()))
                , "a.com");

        //when
        final String rawText = notionBookmarkParser.parseRawText();
        final List<Style> styles = notionBookmarkParser.parseStyles();

        //then
        final String expectedRawText = "a.comtestcaption";
        final List<Style> expectedStyles = List.of(
                new Style(new StyleRange(0, 4), StyleType.LINK)
                , new Style(new StyleRange(5, 15), StyleType.LINK));
        Assertions.assertAll(
                () -> assertThat(rawText).isEqualTo(expectedRawText),
                () -> assertThat(styles).usingRecursiveComparison()
                        .ignoringFields("createdAt", "updatedAt")
                        .isEqualTo(expectedStyles)
        );
    }
}