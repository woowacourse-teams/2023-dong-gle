package org.donggle.backend.application.service.medium.exception;

public abstract class MediumException extends RuntimeException {
    public MediumException(final String message) {
        super(message);
    }

    public MediumException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
