package org.donggle.backend.application.repository;

import org.donggle.backend.domain.BlogWriting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogWritingRepository extends JpaRepository<BlogWriting, Long> {
}
