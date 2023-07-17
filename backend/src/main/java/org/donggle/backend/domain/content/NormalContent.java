package org.donggle.backend.domain.content;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.BlockType;
import org.donggle.backend.domain.Style;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NormalContent extends Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String rawText;
    @OneToMany(mappedBy = "normalContent")
    private List<Style> styles;

    public NormalContent(final int depth, final BlockType blockType, final String rawText, final List<Style> styles) {
        super(depth, blockType);
        this.styles = styles;
        this.rawText = rawText;
    }

    @Override
    public String toString() {
        return "NormalContent{" +
                "type" + getBlockType() +
                "id=" + id +
                ", rawText='" + rawText + '\'' +
                '}';
    }
}
