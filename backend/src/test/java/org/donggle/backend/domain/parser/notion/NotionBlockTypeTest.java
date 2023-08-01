package org.donggle.backend.domain.parser.notion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NotionBlockTypeTest {

    @Test
    @DisplayName("NotionBlockType을 파싱하는 테스트")
    void findNotionBlockType() {
        //given
        //when
        final NotionBlockType blockType = NotionBlockType.findBlockType("heading_1");

        //then
        assertThat(blockType).isEqualTo(NotionBlockType.HEADING_1);
    }

    @Test
    @DisplayName("없는 NotionBlockType을 찾을 경우 예외를 던지는 테스트")
    void findNotionBlockTypeError() {
        //given
        //when
        //then
        assertThatThrownBy(() -> NotionBlockType.findBlockType("존재 안하는 블럭")).isInstanceOf(IllegalArgumentException.class);

    }
}