package org.donggle.backend.exception.authentication;

public class ExpiredAccessTokenException extends UnAuthenticationException {
    private static final String MESSAGE = "토큰이 만료되었습니다.";
    private static final int REFRESH_REQUEST_CODE = 4011;

    public ExpiredAccessTokenException() {
        super(MESSAGE);
    }

    public ExpiredAccessTokenException(final Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public String getHint() {
        return "토큰이 만료되었습니다.";
    }

    @Override
    public int getErrorCode() {
        return REFRESH_REQUEST_CODE;
    }
}
