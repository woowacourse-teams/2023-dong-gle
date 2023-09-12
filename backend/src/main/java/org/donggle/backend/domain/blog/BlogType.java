package org.donggle.backend.domain.blog;

import java.util.Arrays;

public enum BlogType {
    MEDIUM,
    TISTORY,
    ;

    public static BlogType from(final String blogType) {
        return Arrays.stream(BlogType.values())
                .filter(type -> type.name().equalsIgnoreCase(blogType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("토큰 타입 못찾음"));
    }
}
