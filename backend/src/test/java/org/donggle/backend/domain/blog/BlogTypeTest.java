package org.donggle.backend.domain.blog;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.donggle.backend.domain.blog.BlogType.MEDIUM;

class BlogTypeTest {

    @Test
    @DisplayName("지원하고 있는 블로그 타입인지 변환 테스트")
    void from() {
        final BlogType blogType = BlogType.from("MEDIUM");

        assertThat(blogType).isEqualTo(MEDIUM);
    }

    @Test
    @DisplayName("지원하고 있는 블로그 타입인지 변환 테스트")
    void from_not_support() {
        Assertions.assertThatThrownBy(
                () -> BlogType.from("ingpyo")
        ).isInstanceOf(IllegalArgumentException.class);
    }
}