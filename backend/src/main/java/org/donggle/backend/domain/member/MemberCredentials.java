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

    public static MemberCredentials createByTistoryToken(final Member member, final String tistoryToken, final String tistoryBlogName) {
        return new MemberCredentials(null, member, null, null, tistoryToken, tistoryBlogName);
    }

    public static MemberCredentials createByNotionToken(final Member member, final String notionToken) {
        return new MemberCredentials(null, member, notionToken, null, null, null);
    }

    public MemberCredentials updateTistoryToken(final String tistoryToken) {
        this.tistoryToken = tistoryToken;
        return this;
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
