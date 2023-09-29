package org.donggle.backend.application.repository;

import org.donggle.backend.domain.writing.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StyleRepository extends JpaRepository<Style, Long> {

    @Modifying
    @Query("delete from Style s where s.id in :styleIds")
    void deleteAllByIds(@Param("styleIds") final List<Long> styleIds);
}
