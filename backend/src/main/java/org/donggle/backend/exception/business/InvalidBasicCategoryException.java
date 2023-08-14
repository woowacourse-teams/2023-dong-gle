package org.donggle.backend.exception.business;

import org.springframework.http.HttpStatus;

public class InvalidBasicCategoryException extends BusinessException {
    private static final String MESSAGE = "잘못된 요청입니다. 카테고리 이름은 '기본'이 될 수 없습니다.";

    public InvalidBasicCategoryException() {
        super(MESSAGE);
    }

    public InvalidBasicCategoryException(final Throwable cause) {
        super(MESSAGE, cause);
    }
    
    @Override
    public String getHint() {
        return null;
    }
    
    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
