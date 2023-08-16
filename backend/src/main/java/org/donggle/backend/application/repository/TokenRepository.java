package org.donggle.backend.application.repository;

import org.donggle.backend.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberId(final Long memberId);

    void deleteByMemberId(Long memberId);
}
