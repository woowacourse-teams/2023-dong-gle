package org.donggle.backend.exception.business;

import org.springframework.http.HttpStatus;

public class InvalidPublishRequestException extends BusinessException {
    private static final String MESSAGE = "발행 정보가 잘못 입력되었습니다.";

    private final String hint;

    public InvalidPublishRequestException(final String hint) {
        super(MESSAGE);
        this.hint = hint;
    }

    @Override
    public String getHint() {
        return this.hint;
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
