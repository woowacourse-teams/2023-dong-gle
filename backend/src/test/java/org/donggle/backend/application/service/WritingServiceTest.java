package org.donggle.backend.application.service;

import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.request.WritingModifyRequest;
import org.donggle.backend.application.service.writing.WritingFacadeService;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleRange;
import org.donggle.backend.domain.writing.StyleType;
import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.block.Depth;
import org.donggle.backend.domain.writing.block.NormalBlock;
import org.donggle.backend.domain.writing.block.RawText;
import org.donggle.backend.exception.notfound.WritingNotFoundException;
import org.donggle.backend.ui.response.WritingHomeResponse;
import org.donggle.backend.ui.response.WritingListWithCategoryResponse;
import org.donggle.backend.ui.response.WritingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest
class WritingServiceTest {
    @Autowired
    private WritingFacadeService writingService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private WritingRepository writingRepository;

    @BeforeEach
    void setUp() {
        final Member member = memberRepository.findById(1L).get();
        final Category category = categoryRepository.findById(1L).get();
        final Writing writing1 = writingRepository.findById(1L).get();

        final Writing writing2 = writingRepository.save(Writing.of(
                member,
                new Title("테스트 글2"),
                category,
                List.of(
                        new NormalBlock(
                                Depth.from(1),
                                BlockType.PARAGRAPH,
                                RawText.from("테스트 글입니다."),
                                List.of(new Style(new StyleRange(0, 2), StyleType.BOLD)
                                )
                        )
                )
        ));
        final Writing writing3 = writingRepository.save(Writing.of(
                member,
                new Title("테스트 글3"),
                category,
                List.of(
                        new NormalBlock(
                                Depth.from(1),
                                BlockType.PARAGRAPH,
                                RawText.from("테스트 글입니다."),
                                List.of(new Style(new StyleRange(0, 2), StyleType.BOLD)
                                )
                        )
                )
        ));
        writing1.changeNextWriting(writing2);
        writing2.changeNextWriting(writing3);
    }

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
                () -> assertThat(response.writings()).hasSize(3),
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
                () -> assertThat(response.id()).isEqualTo(1L),
                () -> assertThat(response.categoryId()).isEqualTo(writing.getCategory().getId())
        );
    }

    @Test
    @DisplayName("홈 화면에서 페이징된 글을 가져올 수 있다.")
    void findAll1() {
        //given
        final PageRequest pageRequest = PageRequest.of(0, 3);

        //when
        final Page<WritingHomeResponse> responses = writingService.findAll(1L, pageRequest);

        //then
        assertAll(
                () -> assertThat(responses.getSize()).isEqualTo(3),
                () -> assertThat(responses.getTotalPages()).isEqualTo(1),
                () -> assertThat(responses.getTotalElements()).isEqualTo(3),
                () -> assertThat(responses.getNumberOfElements()).isEqualTo(3),
                () -> assertThat(responses.map(WritingHomeResponse::title).toList()).containsExactly("테스트 글3", "테스트 글2", "테스트 글"),
                () -> assertThat(responses.getNumber()).isZero(),
                () -> assertThat(responses.hasNext()).isFalse(),
                () -> assertThat(responses.hasPrevious()).isFalse()
        );
    }

    @Test
    @DisplayName("홈 화면에서 페이징된 글을 가져올 수 있다.")
    void findAll2() {
        //given
        final PageRequest pageRequest = PageRequest.of(0, 2);

        //when
        final Page<WritingHomeResponse> responses = writingService.findAll(1L, pageRequest);

        //then
        assertAll(
                () -> assertThat(responses.getSize()).isEqualTo(2),
                () -> assertThat(responses.getTotalPages()).isEqualTo(2),
                () -> assertThat(responses.getTotalElements()).isEqualTo(3),
                () -> assertThat(responses.getNumberOfElements()).isEqualTo(2),
                () -> assertThat(responses.map(WritingHomeResponse::title).toList()).containsExactly("테스트 글3", "테스트 글2"),
                () -> assertThat(responses.getNumber()).isZero(),
                () -> assertThat(responses.hasNext()).isTrue(),
                () -> assertThat(responses.hasPrevious()).isFalse()
        );
    }

    @Test
    @DisplayName("홈 화면에서 페이징된 글을 가져올 수 있다.")
    void findAll3() {
        //given
        final PageRequest pageRequest = PageRequest.of(1, 2);

        //when
        final Page<WritingHomeResponse> responses = writingService.findAll(1L, pageRequest);

        //then
        assertAll(
                () -> assertThat(responses.getSize()).isEqualTo(2),
                () -> assertThat(responses.getTotalPages()).isEqualTo(2),
                () -> assertThat(responses.getTotalElements()).isEqualTo(3),
                () -> assertThat(responses.getNumberOfElements()).isEqualTo(1),
                () -> assertThat(responses.map(WritingHomeResponse::title).toList()).containsExactly("테스트 글"),
                () -> assertThat(responses.getNumber()).isEqualTo(1),
                () -> assertThat(responses.hasNext()).isFalse(),
                () -> assertThat(responses.hasPrevious()).isTrue()
        );
    }
}
