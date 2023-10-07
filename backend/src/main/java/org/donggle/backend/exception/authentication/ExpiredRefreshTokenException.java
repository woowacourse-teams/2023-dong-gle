package org.donggle.backend.exception.authentication;

public class ExpiredRefreshTokenException extends UnAuthenticationException {
    private static final String MESSAGE = "토큰이 만료되었습니다.";
    private static final int LOGIN_REQUEST_CODE = 4012;

    public ExpiredRefreshTokenException() {
        super(MESSAGE);
    }

    @Override
    public String getHint() {
        return "RefreshToken이 만료되었습니다. 다시 로그인을 진행하세요.";
    }

    @Override
    public int getErrorCode() {
        return LOGIN_REQUEST_CODE;
    }
}
