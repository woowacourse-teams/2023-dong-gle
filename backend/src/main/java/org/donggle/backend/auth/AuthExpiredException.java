package org.donggle.backend.auth;

import org.donggle.backend.exception.business.BusinessException;

public class AuthExpiredException extends BusinessException {

    public AuthExpiredException(final String message) {
        super(message);
    }

    public AuthExpiredException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
