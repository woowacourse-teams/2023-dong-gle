package org.donggle.backend.application.service.medium.exception;

public class MediumForbiddenException extends MediumException {
    private static final String MESSAGE = "Medium 발행 권한이 없습니다.";

    public MediumForbiddenException() {
        super(MESSAGE);
    }

    public MediumForbiddenException(final Throwable cause) {
        super(MESSAGE, cause);
    }
}
