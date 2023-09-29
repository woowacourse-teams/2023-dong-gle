package org.donggle.backend.application.repository;

import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface MemberCredentialsRepository extends JpaRepository<MemberCredentials, Long> {

    Optional<MemberCredentials> findByMember(Member member);

    @Modifying
    void deleteByMember(final Member member);
}
