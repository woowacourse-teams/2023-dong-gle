package org.donggle.backend.exception.notfound;

public abstract class NotFoundException extends RuntimeException {
    public NotFoundException(final String message) {
        super(message);
    }

    public NotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
