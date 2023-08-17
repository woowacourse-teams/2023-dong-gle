package org.donggle.backend.exception.business;

import org.springframework.http.HttpStatus;

public class NotionNotConnectedException extends BusinessException {
    private static final String MESSAGE = "노션 연동이 되어있지 않습니다.";

    public NotionNotConnectedException() {
        super(MESSAGE);
    }

    public NotionNotConnectedException(final Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public String getHint() {
        return "노션을 연동해주세요.";
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
