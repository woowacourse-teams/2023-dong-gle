package org.donggle.backend.application.repository;

import org.donggle.backend.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}
