package org.donggle.backend.application.service.trash;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.exception.notfound.DeleteWritingNotFoundException;
import org.donggle.backend.exception.notfound.RestoreWritingNotFoundException;
import org.donggle.backend.exception.notfound.WritingNotFoundException;
import org.donggle.backend.ui.response.TrashResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.donggle.backend.domain.writing.WritingStatus.DELETED;
import static org.donggle.backend.domain.writing.WritingStatus.TRASHED;

@Service
@Transactional
@RequiredArgsConstructor
public class TrashService {
    private final WritingRepository writingRepository;

    @Transactional(readOnly = true)
    public TrashResponse findTrashedWritingList(final Long memberId) {
        final List<Writing> trashedWritings = writingRepository.findAllByMemberIdAndStatusIsTrashed(memberId);
        return TrashResponse.from(trashedWritings);
    }

    public void trashWritings(final Long memberId, final List<Long> writingIds) {
        writingIds.stream()
                .map(writingId -> {
                    final Writing findWriting = writingRepository.findByIdAndMemberId(memberId, writingId)
                            .orElseThrow(() -> new DeleteWritingNotFoundException(writingId));
                    if (findWriting.getStatus() == DELETED) {
                        throw new IllegalArgumentException();
                    }
                    validateAuthorization(memberId, findWriting);
                    return findWriting;
                })
                .forEach(writing -> {
                    final Writing nextWriting = writing.getNextWriting();
                    writing.moveToTrash();
                    writingRepository.findPreWritingByWritingId(writing.getId())
                            .ifPresent(preWriting -> preWriting.changeNextWriting(nextWriting));
                });
    }

    public void deleteWritings(final Long memberId, final List<Long> writingIds) {
        writingIds.stream()
                .map(writingId -> writingRepository.findByIdAndMemberId(memberId, writingId)
                        .orElseThrow(() -> new WritingNotFoundException(writingId)))
                .forEach(writing -> {
                    if (writing.getStatus() != TRASHED) {
                        throw new IllegalArgumentException();
                    }
                    final Writing nextWriting = writing.getNextWriting();
                    writing.changeNextWritingNull();
                    writingRepository.delete(writing);
                    writingRepository.findPreWritingByWritingId(writing.getId())
                            .ifPresent(preWriting -> preWriting.changeNextWriting(nextWriting));
                });
    }

    public void restoreWritings(final Long memberId, final List<Long> writingIds) {
        writingIds.stream()
                .map(writingId -> writingRepository.findByIdAndMemberId(writingId, memberId)
                        .orElseThrow(() -> new RestoreWritingNotFoundException(writingId)))
                .forEach(writing -> {
                            if (writing.getStatus() != TRASHED) {
                                throw new IllegalArgumentException();
                            }
                            writingRepository.findLastWritingByCategoryId(writing.getCategory().getId())
                                    .ifPresent(lastWriting -> lastWriting.changeNextWriting(writing));
                            writing.restore();
                        }
                );
    }

    private void validateAuthorization(final Long memberId, final Writing writing) {
        if (!writing.isOwnedBy(memberId)) {
            throw new WritingNotFoundException(writing.getId());
        }
    }
}
