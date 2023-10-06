package org.donggle.backend.exception.notfound;

public final class MemberNotFoundException extends NotFoundException {
    private static final String MESSAGE = "존재하지 않는 사용자입니다.";

    private final Long memberId;

    public MemberNotFoundException(final Long memberId) {
        super(MESSAGE);
        this.memberId = memberId;
    }

    @Override
    public String getHint() {
        return "해당 사용자를 찾을 수 없습니다. 입력한 id: " + memberId;
    }
}
