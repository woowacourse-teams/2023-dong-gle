package org.donggle.backend.exception.business;

import org.springframework.http.HttpStatus;

public class DuplicatedMemberException extends BusinessException {
    private static final String MESSAGE = "이미 존재하는 회원 입니다.";
    private final String categoryName;

    public DuplicatedMemberException(final String categoryName) {
        super(MESSAGE);
        this.categoryName = categoryName;
    }

    @Override
    public String getHint() {
        return "이미 존재하는 회원입니다. 가입한 플랫폼: " + categoryName;
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
