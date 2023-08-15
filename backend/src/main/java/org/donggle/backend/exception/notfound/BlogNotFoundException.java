package org.donggle.backend.exception.notfound;

public final class BlogNotFoundException extends NotFoundException {
    private static final String MESSAGE = "존재하지 않는 블로그입니다.";
    
    private final String blogName;

    public BlogNotFoundException(final String blogName) {
        super(MESSAGE);
        this.blogName = blogName;
    }

    public BlogNotFoundException(final String blogName, final Throwable cause) {
        super(MESSAGE, cause);
        this.blogName = blogName;
    }
    
    @Override
    public String getHint() {
        return "해당 블로그를 찾을 수 없습니다. 블로그 이름: " + blogName;
    }
}
