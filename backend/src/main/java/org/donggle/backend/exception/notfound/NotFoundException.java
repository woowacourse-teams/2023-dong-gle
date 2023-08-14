package org.donggle.backend.exception.notfound;

import org.springframework.http.HttpStatus;

public abstract class NotFoundException extends RuntimeException {
    public NotFoundException(final String message) {
        super(message);
    }
    
    public NotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public abstract String getHint();
    
    public int getErrorCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
