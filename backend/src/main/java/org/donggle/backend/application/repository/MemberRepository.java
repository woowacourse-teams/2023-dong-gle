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

    @Query(value = """
            SELECT COUNT(DISTINCT m.id)
            FROM member m
            JOIN writing w ON m.id = w.member_id
            """, nativeQuery = true)
    int addWritings();

    @Query(value = """
            SELECT COUNT(DISTINCT w.member_id) AS PublishedMemberCount
            FROM blog_writing bw
            JOIN writing w ON bw.writing_id = w.id
            WHERE bw.published_at IS NOT NULL
            """, nativeQuery = true)
    int pcount();

}
