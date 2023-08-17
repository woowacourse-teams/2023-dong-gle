package org.donggle.backend.domain.writing.block;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.common.BaseEntity;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Writing;

import java.util.Objects;

@Entity
@Getter
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Block extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writing_id")
    private Writing writing;
    @NotNull
    @Embedded
    private Depth depth;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private BlockType blockType;

    public Block(final Writing writing, final Depth depth, final BlockType blockType) {
        this(null, writing, depth, blockType);
    }

    public Block(final Long id, final Writing writing, final Depth depth, final BlockType blockType) {
        this.id = id;
        this.writing = writing;
        this.depth = depth;
        this.blockType = blockType;
    }

    public int getDepthValue() {
        return this.depth.getDepth();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Block block = (Block) o;
        return Objects.equals(id, block.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", depth=" + depth +
                ", blockType=" + blockType +
                '}';
    }
}
