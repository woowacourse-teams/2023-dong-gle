package org.donggle.backend.infrastructure.client.exception;

import org.springframework.http.HttpStatus;

public class ClientForbiddenException extends ClientException {
    private static final String MESSAGE = "에 접근 권한이 없습니다.";

    private final String platformName;

    public ClientForbiddenException(final String platformName) {
        super(platformName + MESSAGE);
        this.platformName = platformName;
    }

    @Override
    public String getHint() {
        return platformName + "에 접근 권한이 없습니다.";
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.FORBIDDEN.value();
    }
}
