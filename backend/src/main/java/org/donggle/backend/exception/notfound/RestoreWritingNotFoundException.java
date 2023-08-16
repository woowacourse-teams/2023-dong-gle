package org.donggle.backend.exception.notfound;

public class RestoreWritingNotFoundException extends NotFoundException {
    private static final String MESSAGE = "복구할 글을 찾을 수 없습니다.";
    private final Long writingId;

    public RestoreWritingNotFoundException(final Long writingId) {
        super(MESSAGE);
        this.writingId = writingId;
    }

    public RestoreWritingNotFoundException(final Long writingId, final Throwable cause) {
        super(MESSAGE, cause);
        this.writingId = writingId;
    }

    @Override
    public String getHint() {
        return "해당 글을 찾을 수 없습니다. 입력 id: " + writingId;
    }
}
