package org.donggle.backend.domain.writing;

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
    private StyleRange styleRange;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private StyleType styleType;

    public Style(final StyleRange styleRange, final StyleType styleType) {
        this(null, styleRange, styleType);
    }

    public Style(final Long id, final StyleRange styleRange, final StyleType styleType) {
        this.id = id;
        this.styleRange = styleRange;
        this.styleType = styleType;
    }

    public int getStartIndexValue() {
        return styleRange.getStartIndex();
    }

    public int getEndIndexValue() {
        return styleRange.getEndIndex();
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
        return "Style{" +
                "id=" + id +
                ", styleRange=" + styleRange +
                ", styleType=" + styleType +
                '}';
    }
}
