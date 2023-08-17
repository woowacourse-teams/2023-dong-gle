package org.donggle.backend.domain.writing.block;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Writing;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HorizontalRulesBlock extends Block {
    @NotNull
    @Embedded
    private RawText rawText;

    public HorizontalRulesBlock(final Writing writing, final BlockType blockType, final RawText rawText) {
        super(writing, Depth.empty(), blockType);
        this.rawText = rawText;
    }

    public HorizontalRulesBlock(final Writing writing, final Depth depth, final BlockType blockType, final RawText rawText) {
        super(writing, depth, blockType);
        this.rawText = rawText;
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
        final HorizontalRulesBlock that = (HorizontalRulesBlock) o;
        return Objects.equals(rawText, that.rawText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rawText);
    }

    @Override
    public String toString() {
        return "HorizontalRulesBlock{" +
                "rawText=" + rawText +
                '}';
    }
}
