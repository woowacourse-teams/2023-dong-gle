package org.donggle.backend.application.service.notion.exception;

public class NotionNotFoundException extends NotionException {
    private static final String MESSAGE = "찾을 수 없는 리소스입니다.";

    public NotionNotFoundException() {
        super(MESSAGE);
    }

    public NotionNotFoundException(final Throwable cause) {
        super(MESSAGE, cause);
    }
}
