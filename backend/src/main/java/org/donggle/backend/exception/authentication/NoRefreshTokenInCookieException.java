package org.donggle.backend.exception.authentication;

public class NoRefreshTokenInCookieException extends UnAuthenticationException {
    public NoRefreshTokenInCookieException() {
        super(null);
    }

    @Override
    public String getHint() {
        return "쿠키에 RefreshToken이 존재하지 않습니다.";
    }
}
