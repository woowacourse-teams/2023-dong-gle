package org.donggle.backend.domain.writing.content;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.writing.BlockType;

import java.util.Objects;

@Entity
@Getter
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private int depth;
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private BlockType blockType;

    public Content(final int depth, final BlockType blockType) {
        this(null, depth, blockType);
    }

    public Content(final Long id, final int depth, final BlockType blockType) {
        this.id = id;
        this.depth = depth;
        this.blockType = blockType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Content content = (Content) o;
        return Objects.equals(id, content.id);
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
