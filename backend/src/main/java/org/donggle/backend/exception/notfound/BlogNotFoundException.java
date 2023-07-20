package org.donggle.backend.exception.notfound;

public final class BlogNotFoundException extends NotFoundException {
    private static final String MESSAGE = "해당 블로그를 찾을 수 없습니다. 블로그 이름: ";

    public BlogNotFoundException(final String name) {
        super(MESSAGE + name);
    }

    public BlogNotFoundException(final String name, final Throwable cause) {
        super(MESSAGE + name, cause);
    }
}
