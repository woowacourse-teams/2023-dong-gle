package org.donggle.backend.domain.parser;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MarkDownStyleParserTest {
    private MarkDownStyleParser markDownStyleParser;

    @BeforeEach
    void setUp() {
        markDownStyleParser = new MarkDownStyleParser();
    }

    @Test
    @DisplayName("입력된 문자열의 스타일을 전부 제거하는 테스트")
    void removeAllStyles() {
        //given
        final String input = "안`녕하**세요` 여**러분";
        final String result = "안녕하세요 여러분";

        //then
        Assertions.assertThat(markDownStyleParser.removeAllStyles(input)).isEqualTo(result);
    }
}