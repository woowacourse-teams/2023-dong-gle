package org.donggle.backend.auth.exception;

import org.donggle.backend.exception.business.BusinessException;

public class NoSuchTokenException extends BusinessException {
    private static final String MESSAGE = "존재하지 않은 토큰입니다.";

    public NoSuchTokenException() {
        super(MESSAGE);
    }
}
