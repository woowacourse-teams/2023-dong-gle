package org.donggle.backend.application.service.medium.exception;

public class MediumUnAuthorizedException extends MediumException {
    private static final String MESSAGE = "Medium에 인증되지 않은 사용자입니다.";

    public MediumUnAuthorizedException() {
        super(MESSAGE);
    }

    public MediumUnAuthorizedException(final Throwable cause) {
        super(MESSAGE, cause);
    }
}
