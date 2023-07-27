package org.donggle.backend.application.service.notion.exception;

public class NotionForbiddenException extends NotionException {

    private static final String MESSAGE = "Token에 권한이 없습니다.";

    public NotionForbiddenException() {
        super(MESSAGE);
    }

    public NotionForbiddenException(final Throwable cause) {
        super(MESSAGE, cause);
    }
}
