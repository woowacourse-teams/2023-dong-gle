package org.donggle.backend.auth.exception;

import org.donggle.backend.exception.business.BusinessException;

public class InvalidAccessTokenException extends BusinessException {
    private static final String MESSAGE = "유효하지 않은 토큰입니다.";

    public InvalidAccessTokenException() {
        super(MESSAGE);
    }
}
