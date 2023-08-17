package org.donggle.backend.exception.business;

import org.springframework.http.HttpStatus;

public class TistoryNotConnectedException extends BusinessException {
    private static final String MESSAGE = "티스토리 연동이 되어있지 않습니다.";

    public TistoryNotConnectedException() {
        super(MESSAGE);
    }

    public TistoryNotConnectedException(final Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public String getHint() {
        return "티스토리 연동을 해주세요.";
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
