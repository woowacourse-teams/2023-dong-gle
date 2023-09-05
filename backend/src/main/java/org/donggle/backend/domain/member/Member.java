package org.donggle.backend.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Embedded
    private MemberName memberName;
    @Column(unique = true)
    private Long socialId;

    private Member(final MemberName memberName, final Long socialId) {
        this.memberName = memberName;
        this.socialId = socialId;
    }

    public static Member createByKakao(final MemberName memberName, final Long socialId) {
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
