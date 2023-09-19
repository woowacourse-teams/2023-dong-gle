package org.donggle.backend.domain.blog;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.BaseEntity;
import org.donggle.backend.domain.writing.Writing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogWriting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writing_id")
    private Writing writing;
    private LocalDateTime publishedAt;
    @ElementCollection
    private List<String> tags;
    private String url;

    public BlogWriting(final Blog blog, final Writing writing, final LocalDateTime publishedAt, final List<String> tags, final String url) {
        this.blog = blog;
        this.writing = writing;
        this.publishedAt = publishedAt;
        this.tags = tags;
        this.url = url;
    }

    public String getBlogTypeValue() {
        return blog.getBlogType().name();
    }

    public boolean isSameBlogType(final BlogType blogType) {
        return blog.getBlogType() == blogType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BlogWriting that = (BlogWriting) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
