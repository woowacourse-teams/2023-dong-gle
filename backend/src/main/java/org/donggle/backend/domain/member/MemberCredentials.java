package org.donggle.backend.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.common.BaseEntity;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCredentials extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String notionToken;
    private String mediumToken;
    private String tistoryToken;
    private String tistoryBlogName;

    private MemberCredentials(
            final Long id,
            final Member member,
            final String notionToken,
            final String mediumToken,
            final String tistoryToken,
            final String tistoryBlogName
    ) {
        this.id = id;
        this.member = member;
        this.notionToken = notionToken;
        this.mediumToken = mediumToken;
        this.tistoryToken = tistoryToken;
        this.tistoryBlogName = tistoryBlogName;
    }

    public static MemberCredentials basic(final Member newMember) {
        return new MemberCredentials(null, newMember, null, null, null, null);
    }

    public void updateTistory(final String tistoryToken, final String tistoryBlogName) {
        this.tistoryToken = tistoryToken;
        this.tistoryBlogName = tistoryBlogName;
    }

    public void updateMediumToken(final String mediumToken) {
        this.mediumToken = mediumToken;
    }

    public void updateNotionToken(final String notionToken) {
        this.notionToken = notionToken;
    }

    @Override
    public String toString() {
        return "MemberCredentials{" +
                "id=" + id +
                ", member=" + member +
                ", notionToken='" + notionToken + '\'' +
                ", mediumToken='" + mediumToken + '\'' +
                ", tistoryToken='" + tistoryToken + '\'' +
                ", tistoryBlogName='" + tistoryBlogName + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MemberCredentials that = (MemberCredentials) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
