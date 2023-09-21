package org.donggle.backend.domain.member;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.BaseEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET is_deleted = true, deleted_at = now() WHERE id = ?")
@Where(clause = "is_deleted = false")
@Table(uniqueConstraints = @UniqueConstraint(name = "SOCIAL_ID_UNIQUE", columnNames = "socialId"))
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Embedded
    private MemberName memberName;
    private Long socialId;
    private boolean isDeleted = false;
    private LocalDateTime deletedAt;

    private Member(final MemberName memberName, final Long socialId) {
        this.memberName = memberName;
        this.socialId = socialId;
    }

    public Member(final Long id, final MemberName memberName, final Long socialId) {
        this.id = id;
        this.memberName = memberName;
        this.socialId = socialId;
    }

    public static Member of(final MemberName memberName, final Long socialId) {
        return new Member(memberName, socialId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Member member = (Member) o;
        return Objects.equals(id, member.id);
    }
}
