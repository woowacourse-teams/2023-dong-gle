package org.donggle.backend.exception.notfound;

public final class CategoryNotFoundException extends NotFoundException {
    private static final String MESSAGE = "존재하지 않는 카테고리 입니다.";

    private final Long categoryId;

    public CategoryNotFoundException(final Long categoryId) {
        super(MESSAGE);
        this.categoryId = categoryId;
    }

    @Override
    public String getHint() {
        return "해당 카테고리를 찾을 수 없습니다. 입력한 id: " + categoryId;
    }
}
