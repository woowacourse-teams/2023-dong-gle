package org.donggle.backend.application.service.vendor.exception;

import org.springframework.http.HttpStatus;

public class VendorApiNotFoundException extends VendorApiException {
    private static final String MESSAGE = "자원을 찾을 수 없습니다.";

    private final String platformName;

    public VendorApiNotFoundException(final String platformName) {
        super(platformName);
        this.platformName = platformName;
    }

    public VendorApiNotFoundException(final String message, final Throwable cause, final String platformName) {
        super(message, cause);
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
