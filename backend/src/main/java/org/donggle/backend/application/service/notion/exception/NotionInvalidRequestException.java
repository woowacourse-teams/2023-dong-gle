package org.donggle.backend.application.service.notion.exception;

public class NotionInvalidRequestException extends NotionException {
    private static final String MESSAGE = "잘못된 요청입니다.";

    public NotionInvalidRequestException() {
        super(MESSAGE);
    }

    public NotionInvalidRequestException(final Throwable cause) {
        super(MESSAGE, cause);
    }
}
