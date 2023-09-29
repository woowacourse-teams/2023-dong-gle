package org.donggle.backend.application.repository;

import org.donggle.backend.domain.writing.block.Block;
import org.donggle.backend.domain.writing.block.NormalBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlockRepository extends JpaRepository<Block, Long> {

    @Modifying
    @Query("delete from Block b where b.id in :blockIds")
    void deleteAllByIds(@Param("blockIds") List<Long> blockIds);

    @Query("""
            select b
            from Block b
            where b.id in :blockIds and
            type(b) = NormalBlock
            """)
    List<NormalBlock> findNormalBlocksByIds(@Param("blockIds") List<Long> blockIds);
}
