package org.donggle.backend.application.service.vendor.exception;

import reactor.core.publisher.Mono;

public abstract class VendorApiException extends RuntimeException {
    private static final int BAD_REQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;

    public VendorApiException(final String message) {
        super(message);
    }

    public VendorApiException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public static Mono<? extends Throwable> handle4xxException(final int code, final String platformName) {
        return switch (code) {
            case BAD_REQUEST -> Mono.error(new VendorApiRequestException(platformName));
            case UNAUTHORIZED -> Mono.error(new VendorApiUnAuthorizedException(platformName));
            case FORBIDDEN -> Mono.error(new VendorApiForbiddenException(platformName));
            case NOT_FOUND -> Mono.error(new VendorApiNotFoundException(platformName));
            default -> Mono.error(new RuntimeException());
        };
    }

    public static Mono<? extends Throwable> handle5xxException(final String platformName) {
        return Mono.error(new VendorApiInternalServerError(platformName));
    }

    public abstract String getHint();

    public abstract int getErrorCode();
}
