package org.donggle.backend.domain.content;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.BlockType;
import org.donggle.backend.domain.Style;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NormalContent extends Content {
    @Lob
    @NotNull
    private String rawText;
    @OneToMany(mappedBy = "normalContent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Style> styles = new ArrayList<>();

    public NormalContent(final int depth, final BlockType blockType, final String rawText, final List<Style> styles) {
        super(depth, blockType);
        this.rawText = rawText;
        styles.forEach(style -> style.setNormalContent(this));
        this.styles.addAll(styles);
    }

    @Override
    public String toString() {
        return "NormalContent{" +
                "type" + getBlockType() +
                ", rawText='" + rawText + '\'' +
                '}';
    }
}
