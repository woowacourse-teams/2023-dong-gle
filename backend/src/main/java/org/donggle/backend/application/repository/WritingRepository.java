package org.donggle.backend.application.repository;

import org.donggle.backend.domain.writing.Writing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WritingRepository extends JpaRepository<Writing, Long> {
    List<Writing> findAllByCategoryId(final Long categoryId);

    int countByCategoryId(Long id);

    int countByNextWritingId(Long nextWritingId);

    @Query("select w from Writing w " +
            "where w.category.id = :categoryId and " +
            "w.nextWriting is null")
    Optional<Writing> findLastWritingByCategoryId(@Param("categoryId") final Long categoryId);

    @Query("select w from Writing w " +
            "where w.nextWriting.id = :writingId")
    Optional<Writing> findPreWritingByWritingId(@Param("writingId") final Long writingId);

    @Query("select w from Writing w join fetch w.blocks where w.id = :writingId")
    Optional<Writing> findByIdWithBlocks(@Param("writingId") Long writingId);

    @Query(value = "select * from writing w " +
            "where w.member_id = :memberId and " +
            "w.status = 'TRASHED'", nativeQuery = true)
    List<Writing> findAllByMemberIdAndStatusIsTrashed(@Param("memberId") final Long memberId);

    @Query(value = "select * from writing w " +
            "where w.member_id = :memberId and " +
            "w.category_id = :categoryId and " +
            "w.status in ('TRASHED','DELETED')", nativeQuery = true)
    List<Writing> findAllByMemberIdAndCategoryIdAndStatusIsTrashedAndDeleted(@Param("memberId") final Long memberId, @Param("categoryId") final Long categoryId);

    @Query(value = "select * from writing w " +
            "where w.member_id = :memberId and " +
            "w.id = :writingId and " +
            "w.status = 'TRASHED'", nativeQuery = true)
    Optional<Writing> findByMemberIdAndWritingIdAndStatusIsTrashed(@Param("memberId") final Long memberId, @Param("writingId") final Long writingId);

    @Query(value = "select * from writing w " +
            "where w.member_id = :memberId and " +
            "w.id = :writingId and " +
            "w.status != 'DELETED'", nativeQuery = true)
    Optional<Writing> findByMemberIdAndWritingIdAndStatusIsNotDeleted(@Param("memberId") final Long memberId, @Param("writingId") final Long writingId);

    @Query(countQuery = "select count(w) from Writing w where w.member.id = :memberId")
    Page<Writing> findByMemberIdOrderByCreatedAtDesc(@Param("memberId") final Long memberId, final Pageable pageable);
}
