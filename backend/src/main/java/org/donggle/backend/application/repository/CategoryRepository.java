package org.donggle.backend.application.repository;

import org.donggle.backend.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c " +
            "where c.member.id = :memberId and " +
            "c.nextCategory is null"
    )
    Optional<Category> findLastByMemberId(@Param("memberId") final Long memberId);

    List<Category> findAllByMemberId(final Long memberId);
}
