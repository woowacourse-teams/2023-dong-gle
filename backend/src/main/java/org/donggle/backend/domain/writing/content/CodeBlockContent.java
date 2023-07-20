package org.donggle.backend.domain.writing.content;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.writing.BlockType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeBlockContent extends Content {
    @Lob
    @NotNull
    private String rawText;
    @NotNull
    private String language;

    public CodeBlockContent(final int depth, final BlockType blockType, final String rawText, final String language) {
        super(depth, blockType);
        this.rawText = rawText;
        this.language = language;
    }

    @Override
    public String toString() {
        return "CodeBlockContent{" +
                "blocktype=" + getBlockType() +
                ", rawText='" + rawText + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
