package org.donggle.backend.auth.exception;

import org.donggle.backend.exception.authentication.UnAuthenticationException;

public class RefreshTokenNotFoundException extends UnAuthenticationException {
    public RefreshTokenNotFoundException() {
        super(null);
    }
    
    public RefreshTokenNotFoundException(final Throwable cause) {
        super(null, cause);
    }
    
    @Override
    public String getHint() {
        return "존재하지 않는 토큰입니다. 다시 로그인을 진행하세요.";
    }
}
