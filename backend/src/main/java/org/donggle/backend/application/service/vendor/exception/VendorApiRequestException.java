package org.donggle.backend.application.service.vendor.exception;

public class VendorApiRequestException extends VendorApiException {
    private static final String MESSAGE = "잘못된 요청입니다.";

    public VendorApiRequestException() {
        super(MESSAGE);
    }

    public VendorApiRequestException(final Throwable cause) {
        super(MESSAGE, cause);
    }
}
