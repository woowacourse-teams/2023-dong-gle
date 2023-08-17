package org.donggle.backend.domain.writing.block;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Writing;

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

    public ImageBlock(final Writing writing, final BlockType blockType, final ImageUrl imageUrl, final ImageCaption imageCaption) {
        super(writing, Depth.empty(), blockType);
        this.imageUrl = imageUrl;
        this.imageCaption = imageCaption;
    }

    public ImageBlock(final Writing writing, final Depth depth, final BlockType blockType, final ImageUrl imageUrl, final ImageCaption imageCaption) {
        super(writing, depth, blockType);
        this.imageUrl = imageUrl;
        this.imageCaption = imageCaption;
    }

    public String getImageUrlValue() {
        return imageUrl.getImageUrl();
    }

    public String getImageCaptionValue() {
        return imageCaption.getImageCaption();
    }

    @Override
    public String toString() {
        return "ImageContent{" +
                "imageUrl=" + imageUrl +
                ", imageCaption=" + imageCaption +
                '}';
    }
}
