package org.donggle.backend.application.repository;

import org.donggle.backend.domain.content.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
