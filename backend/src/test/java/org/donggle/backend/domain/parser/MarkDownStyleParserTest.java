package org.donggle.backend.domain.parser;

import org.donggle.backend.domain.parser.markdown.MarkDownStyleParser;
import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleRange;
import org.donggle.backend.domain.writing.StyleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MarkDownStyleParserTest {
    private MarkDownStyleParser markDownStyleParser;

    @BeforeEach
    void setUp() {
        markDownStyleParser = new MarkDownStyleParser();
    }

    @Test
    @DisplayName("입력된 문자열의 적용된 스타일을 전부 반환하는 테스트")
    void extractStyles() {
        //given
        final String input = "안`녕하**세요` 여**러분";
        final String originalText = "안녕하세요 여러분";
        final Style codeStyle = new Style(new StyleRange(1, 4), StyleType.CODE);
        final Style boldStyle = new Style(new StyleRange(3, 6), StyleType.BOLD);

        //when
        final List<Style> result = markDownStyleParser.extractStyles(input, originalText);

        //then
        assertAll(
                () -> assertThat(result.get(1)).usingRecursiveComparison().ignoringFields("createdAt", "updatedAt").isEqualTo(codeStyle),
                () -> assertThat(result.get(0)).usingRecursiveComparison().ignoringFields("createdAt", "updatedAt").isEqualTo(boldStyle));
    }

    @Test
    @DisplayName("중복으로 입력된 문자열의 적용된 스타일을 전부 반환하는 테스트")
    void extractStyles_duplicate() {
        //given
        final String input = "**안녕하**세요 **안녕하**세요";
        final String originalText = "안녕하세요 안녕하세요";
        final Style codeStyle = new Style(new StyleRange(0, 2), StyleType.BOLD);
        final Style boldStyle = new Style(new StyleRange(6, 8), StyleType.BOLD);

        //when
        final List<Style> result = markDownStyleParser.extractStyles(input, originalText);

        //then
        assertAll(
                () -> assertThat(result.get(0)).usingRecursiveComparison().ignoringFields("createdAt", "updatedAt").isEqualTo(codeStyle),
                () -> assertThat(result.get(1)).usingRecursiveComparison().ignoringFields("createdAt", "updatedAt").isEqualTo(boldStyle));
    }

    @Test
    @DisplayName("입력된 문자열의 스타일을 전부 제거하는 테스트")
    void removeAllStyles() {
        //given
        final String input = "안`녕하**세요` 여**러분";
        final String result = "안녕하세요 여러분";

        //when
        final String execute = markDownStyleParser.removeStyles(input);

        //then
        assertThat(execute).isEqualTo(result);
    }

    @Test
    @DisplayName("링크 스타일을 파싱하는 테스트")
    void linkParser() {
        //given
        final String input = "[네이버](www.naver.com)";
        final String expected = "www.naver.com네이버";

        //when
        final String result = markDownStyleParser.removeStyles(input);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("링크 스타일을 저장하는 테스트")
    void linkStyleSave() {
        //given
        final String input = "[네이버](www.naver)";
        final String originalText = "www.naver네이버";

        //when
        final List<Style> result = markDownStyleParser.extractStyles(input, originalText);

        //then
        assertAll(
                () -> assertThat(result.get(0).getStyleType()).isEqualTo(StyleType.LINK),
                () -> assertThat(result.get(0).getStartIndexValue()).isEqualTo(0),
                () -> assertThat(result.get(0).getEndIndexValue()).isEqualTo(8),
                () -> assertThat(result.get(1).getStyleType()).isEqualTo(StyleType.LINK),
                () -> assertThat(result.get(1).getStartIndexValue()).isEqualTo(9),
                () -> assertThat(result.get(1).getEndIndexValue()).isEqualTo(11)
        );
    }

    @Test
    @DisplayName("strike through을 파싱하는 테스트")
    void strikeThroughtyleParser() {
        //given
        final String input = "안녕~~하세요~~";
        final String expected = "안녕하세요";

        //when
        final String result = markDownStyleParser.removeStyles(input);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("strike through을 저장하는 테스트")
    void strikeThroughtyleSave() {
        //given
        final String input = "안녕~~하세요~~";
        final String originalText = "안녕하세요";
        Style style = new Style(new StyleRange(2, 4), StyleType.STRIKETHROUGH);

        //when
        final List<Style> result = markDownStyleParser.extractStyles(input, originalText);

        //then
        assertThat(result.get(0)).isEqualTo(style);
    }
}
