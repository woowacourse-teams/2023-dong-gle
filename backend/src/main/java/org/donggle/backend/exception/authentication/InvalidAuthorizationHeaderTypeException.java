package org.donggle.backend.exception.authentication;

public class InvalidAuthorizationHeaderTypeException extends UnAuthenticationException {
    private final String authorizationHeader;

    public InvalidAuthorizationHeaderTypeException(final String authorizationHeader) {
        super(null);
        this.authorizationHeader = authorizationHeader;
    }

    @Override
    public String getHint() {
        return "Authorization 헤더의 타입이 올바르지 않습니다. 입력한 헤더: " + authorizationHeader;
    }
}
