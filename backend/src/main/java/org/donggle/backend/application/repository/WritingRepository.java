package org.donggle.backend.application.repository;

import org.donggle.backend.domain.writing.Writing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WritingRepository extends JpaRepository<Writing, Long> {
}
