package org.donggle.backend.domain.category;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.member.Member;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    private static final CategoryName BASIC_CATEGORY_NAME = new CategoryName("기본");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Embedded
    private CategoryName categoryName;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_category_id")
    private Category nextCategory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Category(final CategoryName categoryName, final Category nextCategory, final Member member) {
        this.categoryName = categoryName;
        this.nextCategory = nextCategory;
        this.member = member;
    }

    public static Category basic(final Member member) {
        return new Category(BASIC_CATEGORY_NAME, null, member);
    }

    public static Category of(final CategoryName categoryName, final Member member) {
        return new Category(categoryName, null, member);
    }

    public String getCategoryNameValue() {
        return categoryName.getName();
    }

    public void changeName(final CategoryName categoryName) {
        this.categoryName = categoryName;
    }

    public void changeNextCategory(final Category nextCategory) {
        this.nextCategory = nextCategory;
    }

    public void changeNextCategoryNull() {
        this.nextCategory = null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
