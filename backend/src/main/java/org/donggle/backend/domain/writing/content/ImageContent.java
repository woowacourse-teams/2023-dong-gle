package org.donggle.backend.domain.writing.content;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.writing.BlockType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageContent extends Content {
    @NotNull
    private String url;
    @NotNull
    private String caption;

    public ImageContent(final int depth, final BlockType blockType, final String url, final String caption) {
        super(depth, blockType);
        this.url = url;
        this.caption = caption;
    }
}
