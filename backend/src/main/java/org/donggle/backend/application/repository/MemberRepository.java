package org.donggle.backend.application.repository;

import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.oauth.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findBySocialIdAndSocialType(final Long socialId, final SocialType socialType);

    @Query("""
            SELECT COUNT(*) AS memberCount
            FROM Member
            WHERE Member.createdAt >= '2023-08-19 00:00:00'
            """)
    int countMember();

}
