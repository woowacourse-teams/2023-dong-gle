package org.donggle.backend.support.fix;

import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.category.CategoryName;

import static org.donggle.backend.support.fix.MemberFixture.beaver_have_id;

public class CategoryFixture {
    public static final Category category = new Category(2L, new CategoryName("카테고리"), null, beaver_have_id);
    public static final Category basicCategory = new Category(10L, new CategoryName("기본"), category, beaver_have_id);

    public static Category categoryBy(final Long id) {
        return new Category(id, new CategoryName("카테고리"), null, beaver_have_id);
    }
}
