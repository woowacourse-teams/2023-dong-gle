package org.donggle.backend.infrastructure.client.exception;

import reactor.core.publisher.Mono;

public abstract class ClientException extends RuntimeException {
    private static final int BAD_REQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;

    public ClientException(final String message) {
        super(message);
    }

    public ClientException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public static Mono<? extends Throwable> handle4xxException(final int code, final String platformName) {
        return switch (code) {
            case BAD_REQUEST -> Mono.error(new ClientRequestException(platformName));
            case UNAUTHORIZED -> Mono.error(new ClientUnAuthorizedException(platformName));
            case FORBIDDEN -> Mono.error(new ClientForbiddenException(platformName));
            case NOT_FOUND -> Mono.error(new ClientNotFoundException(platformName));
            default -> Mono.error(new RuntimeException());
        };
    }

    public static Mono<? extends Throwable> handle5xxException(final String platformName) {
        return Mono.error(new ClientInternalServerError(platformName));
    }

    public abstract String getHint();

    public abstract int getErrorCode();
}
