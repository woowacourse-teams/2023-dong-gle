package org.donggle.backend.domain.writing.content;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageCaption {
    private String imageCaption;

    public ImageCaption(final String imageCaption) {
        this.imageCaption = imageCaption;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ImageCaption that = (ImageCaption) o;
        return Objects.equals(imageCaption, that.imageCaption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageCaption);
    }
}
