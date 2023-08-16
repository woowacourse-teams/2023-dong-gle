package org.donggle.backend.domain.writing.block;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Writing;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeBlock extends Block {
    @NotNull
    @Embedded
    private RawText rawText;
    @NotNull
    @Embedded
    private Language language;

    public CodeBlock(final Writing writing, final BlockType blockType, final RawText rawText, final Language language) {
        super(writing, Depth.empty(), blockType);
        this.rawText = rawText;
        this.language = language;
    }

    public String getRawTextValue() {
        return rawText.getRawText();
    }

    public String getLanguageValue() {
        return language.getLanguage();
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
