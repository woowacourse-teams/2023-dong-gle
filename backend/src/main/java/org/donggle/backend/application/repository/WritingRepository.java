package org.donggle.backend.application.repository;

import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.WritingStatus;
import org.donggle.backend.domain.writing.block.NormalBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WritingRepository extends JpaRepository<Writing, Long> {

    @Query("""
            select w 
            from Writing w
            where w.id = :id and
            w.member.id = :memberId and
            w.status != 'DELETED'
            """)
    Optional<Writing> findByIdAndMemberId(@Param("id") final Long writingId, @Param("memberId") final Long memberId);

    @Query("""
            select w 
            from Writing w
            where w.member.id = :memberId and 
            w.category.id = :categoryId and 
            w.status in (:statuses)
            """)
    List<Writing> findAllByMemberIdAndCategoryIdInStatuses(@Param("memberId") final Long memberId, @Param("categoryId") final Long categoryId, @Param("statuses") final List<WritingStatus> statuses);

    int countByNextWritingIdAndStatus(final Long nextWritingId, final WritingStatus status);

    List<Writing> findAllByCategoryIdAndStatus(final Long categoryId, final WritingStatus status);

    @Query("""
            select w
            from Writing w
            where w.member.id = :memberId and
            w.status = 'TRASHED'
            """)
    List<Writing> findAllByMemberIdAndStatusIsTrashed(@Param("memberId") final Long memberId);

    @Query("""
            select w 
            from Writing w 
            join fetch w.blocks 
            where w.id = :writingId and
            w.status = 'ACTIVE'
            """)
    Optional<Writing> findByIdWithBlocks(@Param("writingId") Long writingId);

    @Query("SELECT b FROM NormalBlock b LEFT JOIN FETCH b.styles WHERE b IN :blocks")
    List<NormalBlock> findStylesForBlocks(@Param("blocks") List<NormalBlock> blocks);

    @Query("""
            select w 
            from Writing w 
            where w.category.id = :categoryId and 
            w.status = 'ACTIVE' and
            w.nextWriting is null
            """)
    Optional<Writing> findLastWritingByCategoryId(@Param("categoryId") final Long categoryId);

    @Query("""
            select w
            from Writing w 
            where w.nextWriting.id = :writingId and 
            w.status = 'ACTIVE'
            """)
    Optional<Writing> findPreWritingByWritingId(@Param("writingId") final Long writingId);

    int countByCategoryIdAndStatus(final Long id, final WritingStatus status);
}
