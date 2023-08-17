package org.donggle.backend.application.service;

import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.request.WritingModifyRequest;
import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.exception.notfound.WritingNotFoundException;
import org.donggle.backend.ui.response.WritingListWithCategoryResponse;
import org.donggle.backend.ui.response.WritingResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
class WritingServiceTest {
    @Autowired
    private WritingService writingService;
    @Autowired
    private WritingRepository writingRepository;

    @Test
    @DisplayName("글 제목 수정 테스트")
    void modifyWritingTitle() {
        //given
        final WritingModifyRequest request = new WritingModifyRequest("글 제목 수정 API 테스트", null, null);
        final Title expected = new Title("글 제목 수정 API 테스트");

        //when
        writingService.modifyWritingTitle(1L, 1L, request);

        //then
        final Writing result = writingRepository.findById(1L)
                .orElseThrow(() -> new WritingNotFoundException(1L));
        assertThat(result.getTitle()).isEqualTo(expected);
    }

    @Test
    @DisplayName("카테고리에 해당하는 글의 목록 조회 테스트")
    void findWritingListByCategoryId() {
        //given
        //when
        final WritingListWithCategoryResponse response = writingService.findWritingListByCategoryId(1L, 1L);

        //then
        assertAll(
                () -> assertThat(response.writings()).hasSize(1),
                () -> assertThat(response.categoryName()).isEqualTo("기본")
        );
    }

    @Test
    @DisplayName("휴지통의 글을 조회하는 테스트")
    void findWritingAndTrashedWriting() {
        //given
        final Writing writing = writingRepository.findById(1L).get();
        writing.moveToTrash();

        //when
        final WritingResponse response = writingService.findWriting(1L, 1L);

        //then
        assertAll(
                () -> assertThat(response.title()).isEqualTo("테스트 글"),
                () -> assertThat(response.categoryId()).isEqualTo(writing.getCategory().getId())
        );
    }
}
