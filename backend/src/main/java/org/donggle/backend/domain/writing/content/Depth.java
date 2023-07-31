package org.donggle.backend.domain.writing.content;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Depth {
    public static final int INITIAL_VALUE = 0;

    @Column(nullable = false)
    private int depth;

    private Depth(final int depth) {
        this.depth = depth;
    }

    public static Depth empty() {
        return new Depth(INITIAL_VALUE);
    }

    public static Depth from(final int depth) {
        return new Depth(depth);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Depth depth1 = (Depth) o;
        return depth == depth1.depth;
    }

    @Override
    public int hashCode() {
        return Objects.hash(depth);
    }

    @Override
    public String toString() {
        return "Depth{" +
                "depth=" + depth +
                '}';
    }
}
