package org.donggle.backend.domain.writing;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StyleIndex {
    private int index;

    public StyleIndex(final int index) {
        validateIndex(index);
        this.index = index;
    }

    private void validateIndex(final int index) {
        if (index < 0) {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StyleIndex that = (StyleIndex) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public String toString() {
        return "StyleIndex{" +
                "index=" + index +
                '}';
    }
}
