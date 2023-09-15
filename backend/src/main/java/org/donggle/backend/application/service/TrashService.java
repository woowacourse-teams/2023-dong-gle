package org.donggle.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.exception.notfound.DeleteWritingNotFoundException;
import org.donggle.backend.exception.notfound.WritingNotFoundException;
import org.donggle.backend.ui.response.TrashResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                    final Writing findWriting = writingRepository.findById(writingId)
                            .orElseThrow(() -> new DeleteWritingNotFoundException(writingId));
                    validateAuthorization(memberId, findWriting);
                    return findWriting;
                })
                .forEach(writing -> {
                    final Long nextWritingId = writing.getNextId();
                    writing.moveToTrash();
                    writingRepository.findPreWritingByWritingId(writing.getId())
                            .ifPresent(preWriting -> preWriting.changeNextWritingId(nextWritingId));
                });
    }

    public void deleteWritings(final Long memberId, final List<Long> writingIds) {
        final List<Writing> trashedWritings = writingRepository.findTrashedWritingsByIds(memberId, writingIds);
        writingRepository.deleteAll(trashedWritings);
    }

    public void restoreWritings(final Long memberId, final List<Long> writingIds) {
        final List<Writing> trashedWritings = writingRepository.findTrashedWritingsByIds(memberId, writingIds);
        for (final Writing trashedWriting : trashedWritings) {
            final Category category = trashedWriting.getCategory();
            if (writingRepository.findLastWritingByCategoryId(category.getId()).isPresent()) {
                final Writing writing = writingRepository.findLastWritingByCategoryId(category.getId()).get();
                writing.changeNextWritingId(trashedWriting.getId());
            }
            trashedWriting.restore();
        }
    }

    private void validateAuthorization(final Long memberId, final Writing writing) {
        if (!writing.isOwnedBy(memberId)) {
            throw new WritingNotFoundException(writing.getId());
        }
    }
}
