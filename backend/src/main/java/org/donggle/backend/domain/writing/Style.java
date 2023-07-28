package org.donggle.backend.domain.writing;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.common.BaseEntity;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Style extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    @AttributeOverride(name = "index", column = @Column(name = "start_index"))
    private StyleIndex startIndex;
    @Embedded
    @AttributeOverride(name = "index", column = @Column(name = "end_index"))
    private StyleIndex endIndex;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private StyleType styleType;

    public Style(final StyleIndex startIndex, final StyleIndex endIndex, final StyleType styleType) {
        this(null, startIndex, endIndex, styleType);
    }

    public Style(final Long id, final StyleIndex startIndex, final StyleIndex endIndex, final StyleType styleType) {
        this.id = id;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.styleType = styleType;
    }

    public int getStartIndexValue() {
        return this.startIndex.getIndex();
    }

    public int getEndIndexValue() {
        return this.endIndex.getIndex();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Style style = (Style) o;
        return Objects.equals(id, style.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Style{" + "startIndex=" + startIndex + ", endIndex=" + endIndex + ", styleType=" + styleType + '}';
    }
}
