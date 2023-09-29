package org.donggle.backend.application.repository;

import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberCredentialsRepository extends JpaRepository<MemberCredentials, Long> {

    Optional<MemberCredentials> findByMember(Member member);

    @Modifying(flushAutomatically = true)
    @Query("delete from MemberCredentials mc where mc.member = :member")
    void deleteByMember(@Param("member") final Member member);
}
