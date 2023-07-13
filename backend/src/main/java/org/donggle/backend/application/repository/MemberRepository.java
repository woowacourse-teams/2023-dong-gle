package org.donggle.backend.application.repository;

import org.donggle.backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
