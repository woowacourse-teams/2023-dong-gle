package org.donggle.backend.exception.business;

import org.donggle.backend.domain.blog.BlogType;
import org.springframework.http.HttpStatus;

public class NotConnectedException extends BusinessException {
    private static final String MESSAGE = " 연동이 되어있지 않습니다.";

    public NotConnectedException(final BlogType blogType) {
        super(blogType.name() + MESSAGE);
    }

    @Override
    public String getHint() {
        return "연동을 해주세요.";
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
