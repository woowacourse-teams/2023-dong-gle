package org.donggle.backend.exception.authentication;

public class ExpiredRefreshTokenException extends UnAuthenticationException {
    private static final String MESSAGE = "유효하지 않은 토큰입니다.";

    public ExpiredRefreshTokenException() {
        super(MESSAGE);
    }

    public ExpiredRefreshTokenException(final Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public String getHint() {
        return "RefreshToken이 만료되었습니다. 다시 로그인을 진행하세요.";
    }
}
