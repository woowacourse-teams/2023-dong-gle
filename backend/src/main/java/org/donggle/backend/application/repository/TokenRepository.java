package org.donggle.backend.application.repository;

import org.donggle.backend.domain.auth.RefreshToken;
import org.donggle.backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberId(final Long memberId);

    @Modifying
    @Query("delete from RefreshToken rt where rt.member.id = :memberId")
    void deleteByMemberId(@Param("memberId") final Long memberId);

    @Modifying(flushAutomatically = true)
    @Query("delete from RefreshToken rt where rt.member = :member")
    void deleteByMember(@Param("member") final Member member);
}
