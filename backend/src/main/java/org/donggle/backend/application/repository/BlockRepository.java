package org.donggle.backend.application.repository;

import org.donggle.backend.domain.writing.content.Block;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Long> {
    List<Block> findAllByWritingId(Long writingId);

    Optional<Block> findById(Long id);
}
