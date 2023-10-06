package org.donggle.backend.exception.business;

import org.springframework.http.HttpStatus;

public class MediumNotConnectedException extends BusinessException {
    private static final String MESSAGE = "미디엄 연동이 되어있지 않습니다.";

    public MediumNotConnectedException() {
        super(MESSAGE);
    }

    public MediumNotConnectedException(final Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public String getHint() {
        return "미디움을 연동해주세요.";
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}