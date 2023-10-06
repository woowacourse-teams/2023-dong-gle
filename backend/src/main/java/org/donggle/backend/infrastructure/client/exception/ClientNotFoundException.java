package org.donggle.backend.infrastructure.client.exception;

import org.springframework.http.HttpStatus;

public class ClientNotFoundException extends ClientException {
    private static final String MESSAGE = "자원을 찾을 수 없습니다.";

    private final String platformName;

    public ClientNotFoundException(final String platformName) {
        super(platformName);
        this.platformName = platformName;
    }

    @Override
    public String getHint() {
        return platformName + "에서 자원을 찾을 수 없습니다.";
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
