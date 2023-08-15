package org.donggle.backend.auth.exception;

import org.donggle.backend.exception.authentication.AuthenticationException;

public class NoRefreshTokenInCookieException extends AuthenticationException {
    public NoRefreshTokenInCookieException() {
        super(null);
    }
    
    public NoRefreshTokenInCookieException(final Throwable cause) {
        super(null, cause);
    }
    
    @Override
    public String getHint() {
        return "쿠키에 RefreshToken이 존재하지 않습니다.";
    }
}
