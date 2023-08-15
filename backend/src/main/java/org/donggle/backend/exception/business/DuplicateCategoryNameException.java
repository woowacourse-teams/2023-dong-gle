package org.donggle.backend.exception.business;

import org.springframework.http.HttpStatus;

public class DuplicateCategoryNameException extends BusinessException {
    private static final String MESSAGE = "이미 존재하는 카테고리 이름입니다.";
    private final String categoryName;

    public DuplicateCategoryNameException(final String categoryName) {
        super(MESSAGE);
        this.categoryName = categoryName;
    }

    public DuplicateCategoryNameException(final String categoryName, final Throwable cause) {
        super(MESSAGE, cause);
        this.categoryName = categoryName;
    }

    @Override
    public String getHint() {
        return "이미 존재하는 카테고리 이름입니다. 입력한 이름: " + categoryName;
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
