package org.donggle.backend.exception.notfound;

public class CategoryNotFoundException extends NotFoundException {
    private static final String MESSAGE = "해당 카테고리를 찾을 수 없습니다. 입력한 id: ";

    public CategoryNotFoundException(final Long id) {
        super(MESSAGE + id);
    }

    public CategoryNotFoundException(final Long id, final Throwable cause) {
        super(MESSAGE + id, cause);
    }
}
