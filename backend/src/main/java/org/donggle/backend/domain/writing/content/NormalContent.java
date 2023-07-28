package org.donggle.backend.domain.writing.content;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Style;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NormalContent extends Content {
    @NotNull
    private RawText rawText;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "normal_content_id", updatable = false, nullable = false)
    private List<Style> styles = new ArrayList<>();

    public NormalContent(final Depth depth, final BlockType blockType, final RawText rawText, final List<Style> styles) {
        super(depth, blockType);
        this.rawText = rawText;
        this.styles = styles;
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
