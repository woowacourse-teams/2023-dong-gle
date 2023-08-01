package org.donggle.backend.domain.writing;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.common.BaseEntity;
import org.donggle.backend.domain.member.Member;

import java.util.Objects;

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
    @Embedded
    private Title title;

    public Writing(final Member member, final Title title) {
        this.member = member;
        this.title = title;
    }

    public void updateTitle(final Title title) {
        this.title = title;
    }

    public String getTitleValue() {
        return this.title.getTitle();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Writing writing = (Writing) o;
        return Objects.equals(id, writing.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
