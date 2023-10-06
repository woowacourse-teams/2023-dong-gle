package org.donggle.backend.infrastructure.client.exception;

import org.springframework.http.HttpStatus;

public class ClientInternalServerError extends ClientException {
    private static final String MESSAGE = " 서버에서 오류가 발생했습니다.";

    private final String platformName;

    public ClientInternalServerError(final String platformName) {
        super(platformName + MESSAGE);
        this.platformName = platformName;
    }

    @Override
    public String getHint() {
        return platformName + " 서버에서 오류가 발생했습니다.";
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
