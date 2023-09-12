package org.donggle.backend.infrastructure.client.exception;

public class ClientInvalidTokenException extends ClientException {
    private static final String MESSAGE = "에 유효하지 않는 토큰입니다.";
    private final String platformName;
    private final int code;

    public ClientInvalidTokenException(final int code, final String platformName) {
        super(platformName + MESSAGE);
        this.platformName = platformName;
        this.code = code;
    }

    @Override
    public String getHint() {
        return platformName + MESSAGE;
    }

    @Override
    public int getErrorCode() {
        return code;
    }
}
