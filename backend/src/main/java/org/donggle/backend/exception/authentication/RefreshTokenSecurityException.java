package org.donggle.backend.exception.authentication;

public class RefreshTokenSecurityException extends UnAuthenticationException {
    private static final String MESSAGE = "조작된 토큰입니다.";

    public RefreshTokenSecurityException() {
        super(null);
    }

    @Override
    public String getHint() {
        return MESSAGE;
    }
}
