package org.donggle.backend.application.service.notion.exception;

public class NotionException extends RuntimeException {
    public NotionException(final String message) {
        super(message);
    }

    public NotionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
