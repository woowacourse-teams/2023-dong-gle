package org.donggle.backend.domain.writing.content;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Style;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Validated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NormalContent extends Content {
    @NotNull
    private RawText rawText;
    @OneToMany(mappedBy = "normalContent", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Style> styles = new ArrayList<>();

    public NormalContent(final Depth depth, final BlockType blockType, final RawText rawText, final List<Style> styles) {
        super(depth, blockType);
        this.rawText = rawText;
        styles.forEach(style -> style.setNormalContent(this));
        this.styles.addAll(styles);
    }

    public String getRawTextValue() {
        return this.rawText.getRawText();
    }

    @Override
    public String toString() {
        return "NormalContent{" +
                "type = " + getBlockType() +
                ", rawText='" + rawText + '\'' +
                '}';
    }
}
