package org.donggle.backend.application.service;

import org.assertj.core.api.Assertions;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.trash.TrashService;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.exception.notfound.DeleteWritingNotFoundException;
import org.donggle.backend.exception.notfound.RestoreWritingNotFoundException;
import org.donggle.backend.fix.WritingFixture;
import org.donggle.backend.ui.response.TrashResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.donggle.backend.domain.writing.WritingStatus.ACTIVE;
import static org.donggle.backend.domain.writing.WritingStatus.TRASHED;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TrashServiceTest {
    @InjectMocks
    private TrashService trashService;
    @Mock
    private WritingRepository writingRepository;


    @Test
    @DisplayName("쓰레기통에 있는 글을 조회한다.")
    void findTrashedWritingList() {
        //given
        final long memberId = 10L;
        given(writingRepository.findAllByMemberIdAndStatusIsTrashed(memberId)).willReturn(WritingFixture.createWritings_ACTIVE());
        //when
        final TrashResponse trashResponse = trashService.findTrashedWritingList(memberId);

        //then
        assertThat(trashResponse.writings()).hasSize(2);
    }

    @Test
    @DisplayName("쓰레기통으로 글을 옮긴 뒤 원래 글의 순서를 변경한다.")
    void changeOrderOfTrashedWriting() {
        //given
        final Long memberId = 10L;
        final List<Long> writingIds = List.of(1L);
        final Writing writing = mock(Writing.class);
        final Writing nextWriting = mock(Writing.class);
        final Writing preWriting = mock(Writing.class);

        given(writingRepository.findByIdAndMemberId(1L, memberId)).willReturn(Optional.of(writing));
        given(writing.getNextWriting()).willReturn(nextWriting);
        given(writingRepository.findPreWritingByWritingId(0L)).willReturn(Optional.of(preWriting));
        given(writing.getStatus()).willReturn(ACTIVE);
        given(writing.isOwnedBy(memberId)).willReturn(true);

        //when
        trashService.trashWritings(memberId, writingIds);

        //then
        then(writing).should().moveToTrash();
        then(preWriting).should().changeNextWriting(nextWriting);
    }

    @Test
    @DisplayName("쓰레기통으로 글을 옮길 때 ACTIVE인 글이 아닐때 에러")
    void changeOrderOfTrashedIsACTIVEWriting() {
        //given
        final Long memberId = 10L;
        final List<Long> writingIds = List.of(1L);
        final Writing writing = mock(Writing.class);

        given(writingRepository.findByIdAndMemberId(1L, memberId)).willReturn(Optional.of(writing));
        given(writing.getStatus()).willReturn(TRASHED);

        //when
        //then
        Assertions.assertThatThrownBy(
                () -> trashService.trashWritings(memberId, writingIds)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("제거할 때 글을 찾을 수 없으면 에러")
    void trashWritings_not_found_delete_writing() {
        //given
        final Long memberId = 10L;
        final List<Long> writingIds = List.of(1L);

        given(writingRepository.findByIdAndMemberId(1L, memberId)).willReturn(Optional.empty());

        //when
        //then
        Assertions.assertThatThrownBy(
                () -> trashService.trashWritings(memberId, writingIds)
        ).isInstanceOf(DeleteWritingNotFoundException.class);
    }


    @Test
    @DisplayName("글을 1개 삭제한다.")
    void deleteWriting() {
        //given
        final Long memberId = 10L;
        final List<Long> writingIds = List.of(1L);
        final Writing writing = mock(Writing.class);

        given(writingRepository.findByIdAndMemberId(1L, memberId)).willReturn(Optional.of(writing));
        given(writing.getStatus()).willReturn(TRASHED);

        //when
        trashService.deleteWritings(memberId, writingIds);

        //then
        then(writingRepository).should().delete(writing);
    }

    @Test
    @DisplayName("글을 2개이상 삭제한다.")
    void deleteWritings() {
        //given
        final Long memberId = 10L;
        final List<Long> writingIds = List.of(1L, 2L);
        final List<Writing> writings = WritingFixture.createWritings_TRASHED();

        given(writingRepository.findByIdAndMemberId(1L, memberId)).willReturn(Optional.of(writings.get(0)));
        given(writingRepository.findByIdAndMemberId(2L, memberId)).willReturn(Optional.of(writings.get(1)));

        //when
        trashService.deleteWritings(memberId, writingIds);

        //then
        then(writingRepository).should().delete(writings.get(0));
        then(writingRepository).should().delete(writings.get(1));
    }

    @Test
    @DisplayName("쓰레기통에 있는 글을 복원한다.")
    void restoreWritings() {
        //given
        final Long memberId = 10L;
        final List<Long> writingIds = List.of(1L);
        final Writing writing1 = mock(Writing.class);
        final Category category = mock(Category.class);

        given(writing1.getCategory()).willReturn(category);
        given(category.getId()).willReturn(1L);
        given(writingRepository.findByIdAndMemberId(1L, memberId)).willReturn(Optional.of(writing1));
        given(writingRepository.findLastWritingByCategoryId(1L)).willReturn(Optional.of(writing1));
        given(writing1.getStatus()).willReturn(TRASHED);

        //when
        trashService.restoreWritings(memberId, writingIds);

        //then
        then(writing1).should(times(1)).restore();
    }

    @Test
    @DisplayName("쓰레기통에 있는 글 찾지 못했을 때 에러")
    void restoreWritings_restore_writing_not_found() {
        //given
        final Long memberId = 10L;
        final List<Long> writingIds = List.of(1L);

        given(writingRepository.findByIdAndMemberId(1L, memberId)).willReturn(Optional.empty());

        //when
        //then
        Assertions.assertThatThrownBy(
                () -> trashService.restoreWritings(memberId, writingIds)
        ).isInstanceOf(RestoreWritingNotFoundException.class);
    }
}
