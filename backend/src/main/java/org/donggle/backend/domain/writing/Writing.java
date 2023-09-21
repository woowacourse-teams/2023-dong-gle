package org.donggle.backend.domain.writing;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.donggle.backend.domain.BaseEntity;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.writing.block.Block;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Where(clause = "status = 'ACTIVE'")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE writing SET status = 'DELETED' WHERE id = ?")
public class Writing extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @NotNull
    @Embedded
    private Title title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "writing_id", nullable = false, updatable = false)
    private List<Block> blocks = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_writing_id")
    private Writing nextWriting;
    @NotNull
    @Enumerated(EnumType.STRING)
    private WritingStatus status = WritingStatus.ACTIVE;

    private Writing(final Member member,
                    final Title title,
                    final Category category,
                    final List<Block> blocks,
                    final Writing nextWriting) {
        this.member = member;
        this.title = title;
        this.category = category;
        this.blocks = blocks;
        this.nextWriting = nextWriting;
    }

    public static Writing lastOf(final Member member, final Title title, final Category category) {
        return new Writing(member, title, category, Collections.emptyList(), null);
    }

    public static Writing of(final Member member, final Title title, final Category category, final List<Block> blocks) {
        return new Writing(member, title, category, blocks, null);
    }

    public void updateTitle(final Title title) {
        this.title = title;
    }

    public void restore() {
        this.status = WritingStatus.ACTIVE;
        changeNextWritingNull();
    }

    public String getTitleValue() {
        return this.title.getTitle();
    }

    public void changeCategory(final Category category) {
        this.category = category;
    }

    public void changeNextWriting(final Writing nextWriting) {
        this.nextWriting = nextWriting;
    }

    public void changeNextWritingNull() {
        this.nextWriting = null;
    }

    public void moveToTrash() {
        this.status = WritingStatus.TRASHED;
        changeNextWritingNull();
    }

    public boolean isOwnedBy(final Long memberId) {
        return getMember().getId().equals(memberId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Writing writing = (Writing) o;
        return Objects.equals(id, writing.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Writing{" +
                "id=" + id +
                ", member=" + member +
                ", title=" + title +
                ", category=" + category +
                ", blocks=" + blocks +
                ", nextWriting=" + nextWriting +
                ", status=" + status +
                '}';
    }
}
