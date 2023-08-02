package org.donggle.backend.exception.business;

public class InvalidBasicCategoryException extends BusinessException {
    private static final String MESSAGE = "기본 카테고리는 변경이 불가합니다.";

    public InvalidBasicCategoryException() {
        super(MESSAGE);
    }

    public InvalidBasicCategoryException(final Throwable cause) {
        super(MESSAGE, cause);
    }
}
