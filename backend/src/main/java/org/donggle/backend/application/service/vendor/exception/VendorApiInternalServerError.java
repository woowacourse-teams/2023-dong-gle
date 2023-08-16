package org.donggle.backend.application.service.vendor.exception;

import org.springframework.http.HttpStatus;

public class VendorApiInternalServerError extends VendorApiException {
    private static final String MESSAGE = " 서버에서 오류가 발생했습니다.";

    private final String platformName;

    public VendorApiInternalServerError(final String platformName) {
        super(platformName + MESSAGE);
        this.platformName = platformName;
    }

    public VendorApiInternalServerError(final Throwable cause, final String platformName) {
        super(MESSAGE, cause);
        this.platformName = platformName;
    }

    @Override
    public String getHint() {
        return platformName + " 서버에서 오류가 발생했습니다.";
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
