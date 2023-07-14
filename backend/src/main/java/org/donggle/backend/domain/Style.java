package org.donggle.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.content.NormalContent;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Style {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "normal_content_id")
    private NormalContent content;
    @Min(0)
    private int startIndex;
    @Min(0)
    private int endIndex;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private StyleType styleType;
}

