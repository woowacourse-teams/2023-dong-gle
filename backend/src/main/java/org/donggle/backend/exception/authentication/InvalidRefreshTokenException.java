package org.donggle.backend.exception.authentication;

public class InvalidRefreshTokenException extends UnAuthenticationException {
    private static final String MESSAGE = "유효하지 않은 토큰입니다.";

    public InvalidRefreshTokenException() {
        super(MESSAGE);
    }

    public InvalidRefreshTokenException(final Throwable cause) {
        super(null, cause);
    }

    @Override
    public String getHint() {
        return "유효하지 않은 RefreshToken입니다. 다시 로그인을 진행하세요.";
    }
}
