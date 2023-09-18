package org.donggle.backend.infrastructure.client.tistory.util;

public enum TistoryApiParameter {
    ACCESS_TOKEN("access_token"),
    OUTPUT("output"),
    BLOG_NAME("blogName"),
    POST_ID("postId");

    private final String parameter;

    TistoryApiParameter(final String parameter) {
        this.parameter = parameter;
    }

    public String parameter() {
        return parameter;
    }
}
