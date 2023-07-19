package org.donggle.backend.exception.notfound;

public final class MemberNotFoundException extends NotFoundException {
    private static final String MESSAGE = "해당 사용자를 찾을 수 없습니다. 입력한 id: ";

    public MemberNotFoundException(final Long id) {
        super(MESSAGE + id);
    }

    public MemberNotFoundException(final Long id, final Throwable cause) {
        super(MESSAGE + id, cause);
    }
}
