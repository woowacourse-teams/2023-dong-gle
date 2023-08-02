package org.donggle.backend.application.service.vendor.exception;

public class VendorApiInternalServerError extends VendorApiException {
    private static final String MESSAGE = " 서버에서 오류가 발생했습니다.";

    public VendorApiInternalServerError(final String platformName) {
        super(platformName + MESSAGE);
    }

    public VendorApiInternalServerError(final Throwable cause) {
        super(MESSAGE, cause);
    }
}
