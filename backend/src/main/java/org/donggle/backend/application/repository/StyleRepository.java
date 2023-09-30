package org.donggle.backend.application.repository;

import org.donggle.backend.domain.writing.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StyleRepository extends JpaRepository<Style, Long> {
    @Override
    @Modifying(flushAutomatically = true)
    @Query("delete from Style s where s.id in :styleIds")
    void deleteAllById(@Param("styleIds") final Iterable<? extends Long> styleIds);
}
