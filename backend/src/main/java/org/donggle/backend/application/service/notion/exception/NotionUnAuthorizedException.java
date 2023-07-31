package org.donggle.backend.application.service.notion.exception;

public class NotionUnAuthorizedException extends NotionException {
    private static final String MESSAGE = "Token이 유효하지 않습니다.";

    public NotionUnAuthorizedException() {
        super(MESSAGE);
    }

    public NotionUnAuthorizedException(final Throwable cause) {
        super(MESSAGE, cause);
    }
}
