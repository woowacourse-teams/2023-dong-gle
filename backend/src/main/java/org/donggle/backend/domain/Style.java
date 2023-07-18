package org.donggle.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Style {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(0)
    private int startIndex;
    @Min(0)
    private int endIndex;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private StyleType styleType;

    public Style(final int startIndex, final int endIndex, final StyleType styleType) {
        this(null, startIndex, endIndex, styleType);
    }

    public Style(final Long id, final int startIndex, final int endIndex, final StyleType styleType) {
        this.id = id;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.styleType = styleType;
    }

    @Override
    public String toString() {
        return "Style{" + "startIndex=" + startIndex + ", endIndex=" + endIndex + ", styleType=" + styleType + '}';
    }
}
