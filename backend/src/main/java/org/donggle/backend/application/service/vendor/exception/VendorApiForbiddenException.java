package org.donggle.backend.application.service.vendor.exception;

import org.springframework.http.HttpStatus;

public class VendorApiForbiddenException extends VendorApiException {
    private static final String MESSAGE = "에 접근 권한이 없습니다.";

    private final String platformName;

    public VendorApiForbiddenException(final String platformName) {
        super(platformName + MESSAGE);
        this.platformName = platformName;
    }

    public VendorApiForbiddenException(final String platformName, final Throwable cause) {
        super(MESSAGE, cause);
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
