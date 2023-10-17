package org.donggle.backend.domain.writing.block;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageUrl {
    @Column(nullable = false)
    private String imageUrl;

    public ImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isUploadedAtDonggle() {
        return imageUrl.contains("image.donggle.blog");
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ImageUrl imageUrl1 = (ImageUrl) o;
        return Objects.equals(imageUrl, imageUrl1.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageUrl);
    }

    @Override
    public String toString() {
        return "ImageUrl{" +
                "imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
