package org.donggle.backend.exception.business;

public abstract class BusinessException extends RuntimeException {
    public BusinessException(final String message) {
        super(message);
    }

    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public abstract String getHint();
    
    public abstract int getErrorCode();
}
