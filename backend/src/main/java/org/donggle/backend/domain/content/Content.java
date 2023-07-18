package org.donggle.backend.domain.content;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.BlockType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int depth;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
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
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", depth=" + depth +
                ", blockType=" + blockType +
                '}';
    }
}
