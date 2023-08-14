package org.donggle.backend.auth.exception;

import org.donggle.backend.exception.authentication.AuthenticationException;

public class EmptyAuthorizationHeaderException extends AuthenticationException {
    public EmptyAuthorizationHeaderException() {
        super(null);
    }
    
    public EmptyAuthorizationHeaderException(final Throwable cause) {
        super(null, cause);
    }
    
    @Override
    public String getHint() {
        return "Authorization 해더값이 존재하지 않습니다.";
    }
}
