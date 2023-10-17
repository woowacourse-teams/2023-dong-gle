package org.donggle.backend.domain.writing.block;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.writing.BlockType;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageBlock extends Block {
    @NotNull
    @Embedded
    private ImageUrl imageUrl;
    @NotNull
    @Embedded
    private ImageCaption imageCaption;

    public ImageBlock(final BlockType blockType, final ImageUrl imageUrl, final ImageCaption imageCaption) {
        super(Depth.empty(), blockType);
        this.imageUrl = imageUrl;
        this.imageCaption = imageCaption;
    }

    public ImageBlock(final Depth depth, final BlockType blockType, final ImageUrl imageUrl, final ImageCaption imageCaption) {
        super(depth, blockType);
        this.imageUrl = imageUrl;
        this.imageCaption = imageCaption;
    }

    public void updateImageUrl(final ImageUrl imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrlValue() {
        return imageUrl.getImageUrl();
    }

    public String getImageCaptionValue() {
        return imageCaption.getImageCaption();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final ImageBlock that = (ImageBlock) o;
        return Objects.equals(imageUrl, that.imageUrl) && Objects.equals(imageCaption, that.imageCaption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), imageUrl, imageCaption);
    }

    @Override
    public String toString() {
        return "ImageContent{" +
                "imageUrl=" + imageUrl +
                ", imageCaption=" + imageCaption +
                '}';
    }
}
