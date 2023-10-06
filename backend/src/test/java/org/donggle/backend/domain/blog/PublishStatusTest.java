package org.donggle.backend.domain.blog;

import org.donggle.backend.exception.business.InvalidPublishRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PublishStatusTest {

    @Test
    @DisplayName("발행 상태 정보가 잘못 입력됐을때 예외")
    void invalidStatusTest() {
        //given
        final String publishStatus = "PURPLE";

        //when
        //then
        assertThatThrownBy(
                () -> PublishStatus.from(publishStatus)
        ).isInstanceOf(InvalidPublishRequestException.class);
    }
}
