package org.donggle.backend.exception.notfound;

public class WritingNotFoundException extends NotFoundException {
    private static final String MESSAGE = "해당 글을 찾을 수 없습니다. 입력 id: ";

    public WritingNotFoundException(final Long writingId) {
        super(MESSAGE + writingId);
    }

    public WritingNotFoundException(final Long writingId, final Throwable cause) {
        super(MESSAGE + writingId, cause);
    }
}
