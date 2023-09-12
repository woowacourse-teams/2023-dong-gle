package org.donggle.backend.domain.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.member.Member;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String refreshToken;
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    public RefreshToken(final String token, final Member member) {
        this.refreshToken = token;
        this.member = member;
    }

    public boolean isDifferentFrom(final String refreshToken) {
        return !this.refreshToken.equals(refreshToken);
    }

    public void update(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "JwtToken{" +
                "id=" + id +
                ", token='" + refreshToken + '\'' +
                ", member=" + member +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RefreshToken refreshToken = (RefreshToken) o;
        return Objects.equals(getId(), refreshToken.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
