package org.donggle.backend.application.repository;

import org.donggle.backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<MemberInfo> findBySocialId(final Long socialId);
}
