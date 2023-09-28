package org.donggle.backend.exception.business;

import org.springframework.http.HttpStatus;

public class InvalidBasicCategoryException extends BusinessException {
    private static final String MESSAGE = "기본 카테고리는 변경이 불가합니다.";

    private final Long sourceCategoryId;
    private final Long targetCategoryId;

    public InvalidBasicCategoryException(final Long sourceCategoryId) {
        super(MESSAGE);
        this.sourceCategoryId = sourceCategoryId;
        this.targetCategoryId = 0L;
    }

    public InvalidBasicCategoryException(final Long sourceCategoryId, final Long targetCategoryId) {
        super(MESSAGE);
        this.sourceCategoryId = sourceCategoryId;
        this.targetCategoryId = targetCategoryId;
    }

    @Override
    public String getHint() {
        return "기본 카테고리는 변경이 불가합니다. 입력한 source id: " + sourceCategoryId + " target id:" + targetCategoryId;
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
