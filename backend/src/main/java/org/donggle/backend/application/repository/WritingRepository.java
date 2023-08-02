package org.donggle.backend.application.repository;

import org.donggle.backend.domain.writing.Writing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WritingRepository extends JpaRepository<Writing, Long> {
    List<Writing> findAllByCategoryId(final Long categoryId);
}
