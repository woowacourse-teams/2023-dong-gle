package org.donggle.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Writing extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @NotNull
    private String title;
    private LocalDateTime publishedAt;
    private boolean isPublished = false;

    public Writing(final Member member, final String title) {
        this.member = member;
        this.title = title;
    }

    public void changePublishStatus(final LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
        this.isPublished = true;
    }
}
