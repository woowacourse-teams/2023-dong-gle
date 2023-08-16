package org.donggle.backend.exception.notfound;

public class WritingNotFoundException extends NotFoundException {
    private static final String MESSAGE = "존재하지 않는 글입니다.";

    private final Long writingId;

    public WritingNotFoundException(final Long writingId) {
        super(MESSAGE);
        this.writingId = writingId;
    }

    public WritingNotFoundException(final Long writingId, final Throwable cause) {
        super(MESSAGE, cause);
        this.writingId = writingId;
    }

    public WritingNotFoundException(final Long writingId, final String message) {
        super(message);
        this.writingId = writingId;
    }

    @Override
    public String getHint() {
        return "해당 글을 찾을 수 없습니다. 입력 id: " + writingId;
    }
}
