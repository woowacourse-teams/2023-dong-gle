package org.donggle.backend.exception.authentication;

import org.donggle.backend.exception.authentication.UnAuthenticationException;

public class InvalidAuthorizationHeaderTypeException extends UnAuthenticationException {
    private final String authorizationHeader;
    
    public InvalidAuthorizationHeaderTypeException(final String authorizationHeader) {
        super(null);
        this.authorizationHeader = authorizationHeader;
    }
    
    public InvalidAuthorizationHeaderTypeException(final String authorizationHeader, final Throwable cause) {
        super(null, cause);
        this.authorizationHeader = authorizationHeader;
    }
    
    @Override
    public String getHint() {
        return "Authorization 헤더의 타입이 올바르지 않습니다. 입력한 헤더: " + authorizationHeader;
    }
}
