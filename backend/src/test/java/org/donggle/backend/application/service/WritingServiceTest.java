package org.donggle.backend.application.service;

import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.request.WritingTitleRequest;
import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.exception.notfound.WritingNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

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
        final WritingTitleRequest request = new WritingTitleRequest("글 제목 수정 API 테스트");
        final Title expected = new Title("글 제목 수정 API 테스트");

        //when
        writingService.modifyWritingTitle(1L, 1L, request);

        //then
        final Writing result = writingRepository.findById(1L)
                .orElseThrow(() -> new WritingNotFoundException(1L));
        assertThat(result.getTitle()).isEqualTo(expected);
    }
}
