package org.donggle.backend.application.repository;

import org.donggle.backend.auth.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<JwtToken, Long> {
    Optional<JwtToken> findByMemberId(final Long memberId);
}
