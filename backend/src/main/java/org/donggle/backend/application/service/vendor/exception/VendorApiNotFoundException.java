package org.donggle.backend.application.service.vendor.exception;

public class VendorApiNotFoundException extends VendorApiException {
    private static final String MESSAGE = "자원을 찾을 수 없습니다.";

    public VendorApiNotFoundException(final String message) {
        super(message);
    }

    public VendorApiNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
