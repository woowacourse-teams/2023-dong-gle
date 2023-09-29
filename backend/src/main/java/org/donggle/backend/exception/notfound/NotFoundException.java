package org.donggle.backend.exception.notfound;

import org.springframework.http.HttpStatus;

public abstract class NotFoundException extends RuntimeException {
    public NotFoundException(final String message) {
        super(message);
    }

    public abstract String getHint();

    public final int getErrorCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
