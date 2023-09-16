package org.donggle.backend.infrastructure.client.exception;

import org.springframework.http.HttpStatus;

public class ClientUnAuthorizedException extends ClientException {
    private static final String MESSAGE = "에 인증되지 않은 사용자입니다.";
    private final String platformName;

    public ClientUnAuthorizedException(final String platformName) {
        super(platformName + MESSAGE);
        this.platformName = platformName;
    }

    public ClientUnAuthorizedException(final String platformName, final Throwable cause) {
        super(MESSAGE, cause);
        this.platformName = platformName;
    }

    @Override
    public String getHint() {
        return platformName + "에 인증되지 않은 사용자입니다.";
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
