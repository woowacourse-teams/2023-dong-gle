package org.donggle.backend.auth.exception;

import org.donggle.backend.exception.authentication.AuthenticationException;

public class ExpiredTokenException extends AuthenticationException {
    private static final String MESSAGE = "유효하지 않은 토큰입니다.";

    public ExpiredTokenException() {
        super(MESSAGE);
    }
    
    public ExpiredTokenException(final Throwable cause) {
        super(MESSAGE, cause);
    }
    
    @Override
    public String getHint() {
        return "AccessToken이 만료되었습니다. RefreshToken값을 요청하세요.";
    }
}
