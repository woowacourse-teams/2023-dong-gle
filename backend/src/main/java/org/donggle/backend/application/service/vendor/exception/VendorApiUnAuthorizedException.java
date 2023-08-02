package org.donggle.backend.application.service.vendor.exception;

public class VendorApiUnAuthorizedException extends VendorApiException {
    private static final String MESSAGE = "에 인증되지 않은 사용자입니다.";

    public VendorApiUnAuthorizedException(final String platformName) {
        super(platformName + MESSAGE);
    }

    public VendorApiUnAuthorizedException(final Throwable cause) {
        super(MESSAGE, cause);
    }
}
