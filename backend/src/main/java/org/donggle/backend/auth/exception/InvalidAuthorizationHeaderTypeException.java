package org.donggle.backend.auth.exception;

import org.donggle.backend.exception.business.BusinessException;

public class InvalidAuthorizationHeaderTypeException extends BusinessException {
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
    
    @Override
    public int getErrorCode() {
        return 403;
    }
}
