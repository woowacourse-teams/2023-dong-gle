package org.donggle.backend.application.service.vendor.exception;

public class VendorApiForbiddenException extends VendorApiException {
    private static final String MESSAGE = "에 접근 권한이 없습니다.";

    public VendorApiForbiddenException(final String platformName) {
        super(platformName + MESSAGE);
    }

    public VendorApiForbiddenException(final Throwable cause) {
        super(MESSAGE, cause);
    }
}
