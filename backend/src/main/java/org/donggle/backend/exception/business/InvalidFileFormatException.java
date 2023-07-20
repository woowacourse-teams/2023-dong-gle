package org.donggle.backend.exception.business;

public class InvalidFileFormatException extends BusinessException {
    private static final String MESSAGE = "지원하지 않는 파일 형식입니다.";

    public InvalidFileFormatException() {
        super(MESSAGE);
    }

    public InvalidFileFormatException(final Throwable cause) {
        super(MESSAGE, cause);
    }
}
