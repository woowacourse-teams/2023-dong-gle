package org.donggle.backend.domain.oauth;

import java.util.Arrays;

public enum SocialType {
    KAKAO,
    ;

    public static SocialType from(final String socialType) {
        return Arrays.stream(SocialType.values())
                .filter(type -> type.name().equalsIgnoreCase(socialType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 소셜 타임이 없습니다."));
    }
}
