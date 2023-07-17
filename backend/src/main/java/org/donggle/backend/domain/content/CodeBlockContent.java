package org.donggle.backend.domain.content;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.BlockType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeBlockContent extends Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String rawText;
    @Column(nullable = false)
    private String language;

    public CodeBlockContent(final int depth, final BlockType blockType, final String rawText, final String language) {
        super(depth, blockType);
        this.rawText = rawText;
        this.language = language;
    }

    @Override
    public String toString() {
        return "CodeBlockContent{" +
                "blocktype=" + getBlockType()+
                "id=" + id +
                ", rawText='" + rawText + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
