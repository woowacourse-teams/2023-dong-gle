package org.donggle.backend.auth.exception;

import org.donggle.backend.exception.business.BusinessException;

public class EmptyAuthorizationHeaderException extends BusinessException {
    private static final String MESSAGE = "header에 Authorization이 존재하지 않습니다.";

    public EmptyAuthorizationHeaderException() {
        super(MESSAGE);
    }
}
