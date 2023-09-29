package org.donggle.backend.fix;

import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.category.CategoryName;

import static org.donggle.backend.fix.MemberFixture.beaver;

public class CategoryFixture {
    public static final Category category = new Category(2L, new CategoryName("카테고리"), null, beaver);
    public static final Category basicCategory = new Category(1L, new CategoryName("기본"), category, beaver);

    public static Category categoryBy(final Long id) {
        return new Category(id, new CategoryName("카테고리"), null, beaver);
    }
}
