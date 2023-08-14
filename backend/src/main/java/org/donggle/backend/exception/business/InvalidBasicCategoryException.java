package org.donggle.backend.exception.business;

import org.springframework.http.HttpStatus;

public class InvalidBasicCategoryException extends BusinessException {
    private static final String MESSAGE = "기본 카테고리는 변경이 불가합니다.";
    
    private final Long categoryId;

    public InvalidBasicCategoryException(final Long categoryId) {
        super(MESSAGE);
        this.categoryId = categoryId;
    }

    public InvalidBasicCategoryException(final Long categoryId, final Throwable cause) {
        super(MESSAGE, cause);
        this.categoryId = categoryId;
    }
    
    @Override
    public String getHint() {
        return "기본 카테고리는 변경이 불가합니다. 입력한 id: " + categoryId;
    }
    
    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
