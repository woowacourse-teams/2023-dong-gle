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
public class RawText {
    @Column(nullable = false, columnDefinition = "TEXT")
    private String rawText;

    private RawText(final String rawText) {
        this.rawText = rawText;
    }

    public static RawText from(final String rawText) {
        return new RawText(rawText);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RawText rawText1 = (RawText) o;
        return Objects.equals(rawText, rawText1.rawText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rawText);
    }

    @Override
    public String toString() {
        return "RawText{" +
                "rawText='" + rawText + '\'' +
                '}';
    }
}
