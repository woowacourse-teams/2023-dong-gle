package org.donggle.backend.application.repository;

import org.donggle.backend.domain.Block;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlockRepository extends JpaRepository<Block, Long> {
    @EntityGraph(attributePaths = {"content"})
    List<Block> findAllByWritingId(Long writingId);
}
