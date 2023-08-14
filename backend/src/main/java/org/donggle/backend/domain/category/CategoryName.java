package org.donggle.backend.domain.category;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryName {
    public static final int CATEGORY_NAME_MAX_LENGTH = 30;
    @Column(length = CATEGORY_NAME_MAX_LENGTH, nullable = false)
    private String name;

    public CategoryName(final String name) {
        this.name = name;
    }

    public boolean isBlank() {
        return name.isBlank();
    }

    public boolean isOverLength() {
        return name.length() > CATEGORY_NAME_MAX_LENGTH;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CategoryName that = (CategoryName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "CategoryName{" +
                "name='" + name + '\'' +
                '}';
    }
}
