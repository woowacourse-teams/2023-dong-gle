package org.donggle.backend.exception.business;

import org.springframework.http.HttpStatus;

public class OverLengthCategoryNameException extends BusinessException {
    private static final String MESSAGE = "카테고리 이름은 30자를 넘을 수 없습니다.";

    public OverLengthCategoryNameException() {
        super(MESSAGE);
    }

    public OverLengthCategoryNameException(final Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public String getHint() {
        return "카테고리 이름은 30자를 넘을 수 없습니다.";
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}