package org.donggle.backend.application.service.medium.exception;

public class MediumBadRequestException extends MediumException {
    private static final String MESSAGE = "Medium 발행 요청 속성이 잘못되었습니다.";

    public MediumBadRequestException() {
        super(MESSAGE);
    }

    public MediumBadRequestException(final Throwable cause) {
        super(MESSAGE, cause);
    }
}
