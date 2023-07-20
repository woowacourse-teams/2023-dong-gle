package org.donggle.backend.application.service.medium.exception;

public class MediumInternalServerError extends MediumException {
    private static final String MESSAGE = "Medium 서버에서 오류가 발생했습니다.";

    public MediumInternalServerError() {
        super(MESSAGE);
    }

    public MediumInternalServerError(final Throwable cause) {
        super(MESSAGE, cause);
    }
}
