package org.donggle.backend.application.repository;

import org.donggle.backend.application.repository.dto.MemberInfo;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.oauth.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<MemberInfo> findBySocialIdAndSocialType(final Long socialId, final SocialType socialType);

    @Override
    @Modifying
    @Query("delete from Member m where m = :member")
    void delete(@Param("member") final Member member);
}
