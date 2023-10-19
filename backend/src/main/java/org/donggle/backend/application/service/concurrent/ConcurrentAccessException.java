package org.donggle.backend.application.service.concurrent;

import org.springframework.http.HttpStatus;

public class ConcurrentAccessException extends RuntimeException {
    private static final String MESSAGE = "동시에 접근할 수 없습니다.";

    public ConcurrentAccessException() {
        super(MESSAGE);
    }

    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
