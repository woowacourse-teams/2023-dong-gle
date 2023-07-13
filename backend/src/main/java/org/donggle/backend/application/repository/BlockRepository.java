package org.donggle.backend.application.repository;

import org.donggle.backend.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
}
