package org.donggle.backend.exception.authentication;

public class InvalidRefreshTokenException extends UnAuthenticationException {
    public InvalidRefreshTokenException() {
        super(null);
    }

    @Override
    public String getHint() {
        return "유효하지 않은 RefreshToken입니다. 다시 로그인을 진행하세요.";
    }
}
