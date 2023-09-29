package org.donggle.backend.application.repository;

import org.donggle.backend.domain.blog.BlogWriting;
import org.donggle.backend.domain.writing.Writing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogWritingRepository extends JpaRepository<BlogWriting, Long> {
    List<BlogWriting> findByWritingId(final Long writingId);

    @Query("SELECT bw FROM BlogWriting bw LEFT JOIN FETCH bw.blog WHERE bw.writing IN :writings")
    List<BlogWriting> findWithFetch(@Param("writings") List<Writing> writings);

    @Modifying(flushAutomatically = true)
    @Query("delete from BlogWriting bw where bw.writing in :writings")
    void deleteAllByWritings(@Param("writings") final List<Writing> writings);
}
