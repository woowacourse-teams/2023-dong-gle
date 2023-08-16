package org.donggle.backend.application.service.vendor.exception;

import org.springframework.http.HttpStatus;

public class VendorApiRequestException extends VendorApiException {
    private static final String MESSAGE = "잘못된 요청입니다.";

    private final String platformName;

    public VendorApiRequestException(final String platformName) {
        super(MESSAGE);
        this.platformName = platformName;
    }

    public VendorApiRequestException(final Throwable cause, final String platformName) {
        super(MESSAGE, cause);
        this.platformName = platformName;
    }

    @Override
    public String getHint() {
        return "잘못된 요청입니다. 요청한 플랫폼: " + platformName;
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
