package org.donggle.backend.exception.authentication;

import org.springframework.http.HttpStatus;

public abstract class UnAuthenticationException extends RuntimeException {
    public UnAuthenticationException(final String message) {
        super(message);
    }

    public abstract String getHint();

    public int getErrorCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
