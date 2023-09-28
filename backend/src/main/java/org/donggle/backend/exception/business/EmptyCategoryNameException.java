package org.donggle.backend.exception.business;

import org.springframework.http.HttpStatus;

public class EmptyCategoryNameException extends BusinessException {
    private static final String MESSAGE = "카테고리 이름은 빈 값이 될 수 없습니다.";

    public EmptyCategoryNameException() {
        super(MESSAGE);
    }

    @Override
    public String getHint() {
        return "카테고리 이름은 빈 값이 될 수 없습니다.";
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
