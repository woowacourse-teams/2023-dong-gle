package org.donggle.backend.infrastructure.client.exception;

import org.springframework.http.HttpStatus;

public class ClientRequestException extends ClientException {
    private static final String MESSAGE = "잘못된 요청입니다.";

    private final String platformName;

    public ClientRequestException(final String platformName) {
        super(MESSAGE);
        this.platformName = platformName;
    }

    @Override
    public String getHint() {
        return "잘못된 요청입니다. 요청한 플랫폼: " + platformName;
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
