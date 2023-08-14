package org.donggle.backend.exception.authentication;

import org.springframework.http.HttpStatus;

public abstract class AuthenticationException extends RuntimeException {
    public AuthenticationException(final String message) {
        super(message);
    }
    
    public AuthenticationException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public abstract String getHint();
    
    public final int getErrorCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
