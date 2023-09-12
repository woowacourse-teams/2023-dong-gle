package org.donggle.backend.exception.authentication;

import org.donggle.backend.exception.authentication.UnAuthenticationException;

public class ExpiredAccessTokenException extends UnAuthenticationException {
    private static final String MESSAGE = "유효하지 않은 토큰입니다.";
    private static final int REFRESH_REQUEST_CODE = 4011;

    public ExpiredAccessTokenException() {
        super(MESSAGE);
    }

    public ExpiredAccessTokenException(final Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public String getHint() {
        return "AccessToken이 만료되었습니다. RefreshToken값을 요청하세요.";
    }

    @Override
    public int getErrorCode() {
        return REFRESH_REQUEST_CODE;
    }
}
