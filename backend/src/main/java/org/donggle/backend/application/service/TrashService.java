package org.donggle.backend.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.ui.response.TrashResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TrashService {
    private final WritingRepository writingRepository;

    public TrashResponse findTrashedWritingList(final Long memberId) {
        final List<Writing> trashedWritings = writingRepository.findAllByMemberIdAndStatusIsTrashed(memberId);
        return TrashResponse.from(trashedWritings);
    }

    public void trashWritings(final Long memberId, final List<Long> writingIds) {
        checkEmptyWritings(writingIds);
        writingIds.stream()
                .map(writingId -> writingRepository.findByMemberIdAndId(memberId, writingId)
                        .orElseThrow(() -> new IllegalArgumentException("삭제할 수 없는 글이 포함되어 있습니다.")))
                .forEach(writing -> {
                    final Writing nextWriting = writing.getNextWriting();
                    writing.moveToTrash();
                    writingRepository.findPreWritingByWritingId(writing.getId())
                            .ifPresent(preWriting -> preWriting.changeNextWriting(nextWriting));
                });
    }

    public void deleteWritings(final Long memberId, final List<Long> writingIds) {
        checkEmptyWritings(writingIds);
        writingIds.stream()
                .map(writingId -> writingRepository.findByMemberIdAndIdAndStatusIsNotDeleted(memberId, writingId)
                        .orElseThrow(() -> new IllegalArgumentException("삭제할 수 없는 글이 포함되어 있습니다.")))
                .forEach(writing -> {
                    final Writing nextWriting = writing.getNextWriting();
                    writingRepository.delete(writing);
                    writingRepository.findPreWritingByWritingId(writing.getId())
                            .ifPresent(preWriting -> preWriting.changeNextWriting(nextWriting));
                });
    }

    public void restoreWritings(final Long memberId, final List<Long> writingIds) {
        checkEmptyWritings(writingIds);
        writingIds.stream()
                .map(writingId -> writingRepository.findByMemberIdAndIdAndStatusIsNotDeleted(memberId, writingId)
                        .orElseThrow(() -> new IllegalArgumentException("복원할 수 없는 글이 포함되어 있습니다.")))
                .forEach(writing -> {
                            writingRepository.findLastWritingByCategoryId(writing.getCategory().getId())
                                    .ifPresent(lastWriting -> lastWriting.changeNextWriting(writing));
                            writing.restore();
                        }
                );
    }

    private static void checkEmptyWritings(final List<Long> writingIds) {
        if (writingIds.isEmpty()) {
            throw new IllegalArgumentException("삭제할 글이 없습니다.");
        }
    }
}
