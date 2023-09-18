package org.donggle.backend.domain.blog;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PublishStatus {
    PUBLIC(3, "public"),
    PRIVATE(0, "draft"),
    PROTECT(1, "unlisted");

    private final int tistory;
    private final String medium;

    PublishStatus(final int tistory, final String medium) {
        this.tistory = tistory;
        this.medium = medium;
    }

    public static PublishStatus from(final String publishStatus) {
        return Arrays.stream(PublishStatus.values())
                .filter(status -> status.name().equalsIgnoreCase(publishStatus))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("발행 상태 정보가 잘못 입력되었습니다."));
    }
}
