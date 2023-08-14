package org.donggle.backend.exception.business;

public class EmptyCategoryNameException extends BusinessException {
    private static final String MESSAGE = "카테고리 이름은 빈 값이 될 수 없습니다.";

    public EmptyCategoryNameException() {
        super(MESSAGE);
    }

    public EmptyCategoryNameException(final Throwable cause) {
        super(MESSAGE, cause);
    }
}
