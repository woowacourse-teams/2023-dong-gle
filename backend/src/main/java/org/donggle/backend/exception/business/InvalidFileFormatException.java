package org.donggle.backend.exception.business;

import org.springframework.http.HttpStatus;

public class InvalidFileFormatException extends BusinessException {
    private static final String MESSAGE = "지원하지 않는 파일 형식입니다.";
    
    private final String originalFileName;

    public InvalidFileFormatException(final String originalFilename) {
        super(MESSAGE);
        this.originalFileName = originalFilename;
    }

    public InvalidFileFormatException(final String originalFileName, final Throwable cause) {
        super(MESSAGE, cause);
        this.originalFileName = originalFileName;
    }
    
    @Override
    public String getHint() {
        return "해당 파일은 지원하지 않습니다. 입력한 파일 : " + originalFileName;
    }
    
    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
