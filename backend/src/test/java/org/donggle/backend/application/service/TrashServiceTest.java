package org.donggle.backend.application.service;

import jakarta.transaction.Transactional;
import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.ui.response.TrashResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TrashServiceTest {
    @Autowired
    private WritingRepository writingRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TrashService trashService;

    @BeforeEach
    void setUp() {
        final Writing writing1 = writingRepository.findById(1L).get();
        final Member member = memberRepository.findById(1L).get();
        final Category category = categoryRepository.findFirstByMemberId(1L).get();
        final Writing writing2 = Writing.lastOf(member, new Title("test2"), category);
        final Writing writing3 = Writing.lastOf(member, new Title("test3"), category);
        final Writing writing4 = Writing.lastOf(member, new Title("test4"), category);
        writingRepository.save(writing2);
        writingRepository.save(writing3);
        writingRepository.save(writing4);
        writing1.changeNextWriting(writing2);
        writing2.changeNextWriting(writing3);
        writing3.changeNextWriting(writing4);
    }

    @Test
    @DisplayName("쓰레기통에 있는 글을 조회한다.")
    void findTrashedWritingList() {
        //given
        final Writing writing = writingRepository.findById(1L).get();
        writing.moveToTrash();

        //when
        final TrashResponse trashResponse = trashService.findTrashedWritingList(1L);

        //then
        assertAll(
                () -> assertThat(trashResponse.writings()).hasSize(1),
                () -> assertThat(trashResponse.writings()).usingRecursiveComparison()
                        .comparingOnlyFields("id")
                        .isEqualTo(writing.getId())
        );
    }

    @Test
    @DisplayName("쓰레기통으로 글을 옮긴 뒤 원래 글의 순서를 변경한다. - 가운데")
    void changeOrderOfTrashedWritingsMiddle() {
        //given
        //when
        trashService.trashWritings(1L, List.of(3L));

        //then
        final List<Writing> writings = writingRepository.findAllByCategoryId(1L);
        assertAll(
                () -> assertThat(writings).hasSize(3),
                () -> assertThat(writings.get(0).getNextWriting()).isEqualTo(writings.get(1))
        );
    }

    @Test
    @DisplayName("쓰레기통으로 글을 옮긴 뒤 원래 글의 순서를 변경한다. - 마지막")
    void changeOrderOfTrashedWritingsLast() {
        //given
        final Writing writing = writingRepository.findById(4L).get();
        
        //when
        trashService.trashWritings(1L, List.of(writing.getId()));

        //then
        final List<Writing> writings = writingRepository.findAllByCategoryId(1L);
        assertAll(
                () -> assertThat(writings).hasSize(3),
                () -> assertThat(writings.get(0).getNextWriting()).isEqualTo(writings.get(1))
        );
    }

    @Test
    @DisplayName("쓰레기통으로 글을 옮긴 뒤 원래 글의 순서를 변경한다. - 첫번째")
    void changeOrderOfTrashedWritingsFirst() {
        //given
        final Writing writing = writingRepository.findById(1L).get();
        //when
        trashService.trashWritings(1L, List.of(writing.getId()));

        //then
        final List<Writing> writings = writingRepository.findAllByCategoryId(1L);
        assertAll(
                () -> assertThat(writings).hasSize(3),
                () -> assertThat(writings.get(0).getNextWriting()).isEqualTo(writings.get(1))
        );
    }

    @Test
    @DisplayName("글을 삭제한다.")
    void deleteWritings() {
        //given

        //when
        trashService.deleteWritings(1L, List.of(1L));

        //then
        assertThat(writingRepository.findById(1L)).isEmpty();
    }

    @Test
    @DisplayName("쓰레기통에 있는 글을 복원한다.")
    void restoreWritings() {
        //given
        final Writing writing = writingRepository.findById(1L).get();
        writing.moveToTrash();

        //when
        trashService.restoreWritings(1L, List.of(1L));

        //then
        final List<Writing> writings = writingRepository.findAllByCategoryId(1L);
        final Optional<Writing> restoredWriting = writingRepository.findById(1L);
        assertAll(
                () -> assertThat(restoredWriting).isNotEmpty(),
                () -> assertThat(writings).hasSize(4),
                () -> assertThat(restoredWriting.get().getNextWriting()).isNull()
        );
    }
}
