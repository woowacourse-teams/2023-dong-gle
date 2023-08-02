package org.donggle.backend.application.repository;

import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberCredentialsRepository extends JpaRepository<MemberCredentials, Long> {

    Optional<MemberCredentials> findMemberCredentialsByMember(Member member);
}
