package org.donggle.backend.domain.parser;

import org.donggle.backend.domain.Style;
import org.donggle.backend.domain.StyleType;
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
        final Style codeStyle = new Style(1, 4, StyleType.CODE);
        final Style boldStyle = new Style(3, 6, StyleType.BOLD);

        //when
        final List<Style> result = markDownStyleParser.extractStyles(input, originalText);

        //then
        assertAll(
                () -> assertThat(result.get(1)).usingRecursiveComparison().isEqualTo(codeStyle),
                () -> assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(boldStyle));
    }

    @Test
    @DisplayName("입력된 문자열의 스타일을 전부 제거하는 테스트")
    void removeAllStyles() {
        //given
        final String input = "안`녕하**세요` 여**러분";
        final String result = "안녕하세요 여러분";

        //when
        System.out.println("input = " + input);
        final String execute = markDownStyleParser.removeStyles(input);

        //then
        assertThat(execute).isEqualTo(result);
    }
}