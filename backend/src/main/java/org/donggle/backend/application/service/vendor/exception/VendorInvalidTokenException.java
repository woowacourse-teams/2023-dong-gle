package org.donggle.backend.application.service.vendor.exception;

public class VendorInvalidTokenException extends VendorApiException {
    private static final String MESSAGE = "에 유효하지 않는 토큰입니다.";
    private final String platformName;
    private final int code;

    public VendorInvalidTokenException(final int code, final String platformName) {
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
