package org.donggle.backend.exception.authentication;

import org.donggle.backend.exception.authentication.UnAuthenticationException;

public class InvalidRefreshTokenException extends UnAuthenticationException {
    public InvalidRefreshTokenException() {
        super(null);
    }
    
    public InvalidRefreshTokenException(final Throwable cause) {
        super(null, cause);
    }
    
    @Override
    public String getHint() {
        return "유효하지 않은 RefreshToken입니다. 다시 로그인을 진행하세요.";
    }
}
