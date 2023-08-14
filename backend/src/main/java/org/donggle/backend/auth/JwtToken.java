package org.donggle.backend.auth;

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
public class JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String refreshToken;
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    public JwtToken(final String token, final Member member) {
        this.refreshToken = token;
        this.member = member;
    }

    public boolean isDifferentRefreshToken(final String refreshToken) {
        return !this.refreshToken.equals(refreshToken);
    }

    public void updateRefreshToken(final String refreshToken) {
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
        final JwtToken jwtToken = (JwtToken) o;
        return Objects.equals(getId(), jwtToken.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
