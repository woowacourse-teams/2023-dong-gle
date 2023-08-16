package org.donggle.backend.exception.business;

import org.donggle.backend.domain.blog.BlogType;
import org.springframework.http.HttpStatus;

public class WritingAlreadyPublishedException extends BusinessException {
    private static final String MESSAGE = "이미 발행된 글입니다.";
    
    private final Long writingId;
    private final BlogType blogType;

    public WritingAlreadyPublishedException(final Long writingId, final BlogType blogType) {
        super(MESSAGE);
        this.writingId = writingId;
        this.blogType = blogType;
    }

    public WritingAlreadyPublishedException(final Long writingId, final BlogType blogType, final Throwable cause) {
        super(MESSAGE, cause);
        this.writingId = writingId;
        this.blogType = blogType;
    }

    @Override
    public String getHint() {
        return "이미 발행된 글입니다. 입력한 id: " + writingId + ", 블로그 이름: " + blogType.name();
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
