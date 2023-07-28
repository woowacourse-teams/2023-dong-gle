package org.donggle.backend.domain.parser;

import org.donggle.backend.domain.parser.markdown.MarkDownStyleParser;
import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleIndex;
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
        final Style codeStyle = new Style(new StyleIndex(1), new StyleIndex(4), StyleType.CODE);
        final Style boldStyle = new Style(new StyleIndex(3), new StyleIndex(6), StyleType.BOLD);

        //when
        final List<Style> result = markDownStyleParser.extractStyles(input, originalText);

        //then
        assertAll(
                () -> assertThat(result.get(1)).usingRecursiveComparison().isEqualTo(codeStyle),
                () -> assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(boldStyle));
    }

    @Test
    @DisplayName("중복으로 입력된 문자열의 적용된 스타일을 전부 반환하는 테스트")
    void extractStyles_duplicate() {
        //given
        final String input = "**안녕하**세요 **안녕하**세요";
        final String originalText = "안녕하세요 안녕하세요";
        final Style codeStyle = new Style(new StyleIndex(0), new StyleIndex(2), StyleType.BOLD);
        final Style boldStyle = new Style(new StyleIndex(6), new StyleIndex(8), StyleType.BOLD);

        //when
        final List<Style> result = markDownStyleParser.extractStyles(input, originalText);

        //then
        assertAll(
                () -> assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(codeStyle),
                () -> assertThat(result.get(1)).usingRecursiveComparison().isEqualTo(boldStyle));
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
        final String expected = "네이버www.naver.com";

        //when
        final String result = markDownStyleParser.removeStyles(input);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("링크 스타일을 저장하는 테스트")
    void linkStyleSave() {
        //given
        final String input = "[네이버](www)";
        final String originalText = "네이버www";
        final Style caption = new Style(new StyleIndex(0), new StyleIndex(2), StyleType.LINK);
        final Style url = new Style(new StyleIndex(3), new StyleIndex(5), StyleType.LINK);

        //when
        final List<Style> result = markDownStyleParser.extractStyles(input, originalText);

        //then
        assertAll(
                () -> assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(caption),
                () -> assertThat(result.get(1)).usingRecursiveComparison().isEqualTo(url)
        );
    }
}
