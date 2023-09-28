package org.donggle.backend.exception.business;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DuplicateCategoryNameExceptionTest {
    @Test
    @DisplayName("DuplicateCategoryNameException의 반환값 테스트")
    void getValue() {
        //given
        //when
        final DuplicateCategoryNameException duplicateCategoryNameException = new DuplicateCategoryNameException("기본");

        //then
        assertThat(duplicateCategoryNameException.getHint()).isEqualTo("이미 존재하는 카테고리 이름입니다. 입력한 이름: 기본");
        assertThat(duplicateCategoryNameException.getErrorCode()).isEqualTo(400);
    }

}