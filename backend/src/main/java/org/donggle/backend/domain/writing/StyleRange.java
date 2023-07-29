package org.donggle.backend.domain.writing;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StyleRange {
    private static final int MIN = 0;

    @Column(nullable = false)
    private int startIndex;
    @Column(nullable = false)
    private int endIndex;

    public StyleRange(final int startIndex, final int endIndex) {
        validateIndex(startIndex, endIndex);
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    private void validateIndex(final int startIndex, final int endIndex) {
        if ((startIndex < MIN || endIndex < MIN) && (startIndex > endIndex)) {
            throw new IllegalArgumentException();
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
        final StyleRange that = (StyleRange) o;
        return getStartIndex() == that.getStartIndex() && getEndIndex() == that.getEndIndex();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartIndex(), getEndIndex());
    }

    @Override
    public String toString() {
        return "StyleRange{" +
                "startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                '}';
    }
}
