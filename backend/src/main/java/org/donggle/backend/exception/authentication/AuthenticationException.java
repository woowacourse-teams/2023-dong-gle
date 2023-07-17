package org.donggle.backend.exception.authentication;

public abstract class AuthenticationException extends RuntimeException {
    public AuthenticationException(final String message) {
        super(message);
    }

    public AuthenticationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
