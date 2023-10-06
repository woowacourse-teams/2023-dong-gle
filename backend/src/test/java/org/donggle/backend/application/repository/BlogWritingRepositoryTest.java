package org.donggle.backend.application.repository;

import org.donggle.backend.domain.blog.Blog;
import org.donggle.backend.domain.blog.BlogWriting;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.category.CategoryName;
import org.donggle.backend.domain.encryption.AESEncryptionUtil;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.donggle.backend.domain.blog.BlogType.MEDIUM;
import static org.donggle.backend.support.fix.MemberFixture.beaver_have_not_id;

@DataJpaTest
class BlogWritingRepositoryTest {
    @Autowired
    private BlogWritingRepository blogWritingRepository;
    @Autowired
    private WritingRepository writingRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BlogRepository blogRepository;
    @MockBean
    private AESEncryptionUtil aesEncryptionUtil;

    @Test
    @DisplayName("글의 발행정보를 불러오는 테스트")
    void findByWritingId() {
        //given
        final Blog blog = new Blog(MEDIUM);
        final List<String> tags = List.of("안녕");
        final String url = "https://donggle.blog";
        final Member member = memberRepository.save(beaver_have_not_id);
        final Category category = new Category(new CategoryName("카테고리"), null, member);
        final Writing writing = Writing.of(member, new Title("Title 1"), category, new ArrayList<>());
        blogRepository.save(blog);
        categoryRepository.save(category);
        final Writing saveWriting = writingRepository.save(writing);
        final BlogWriting blogWriting = new BlogWriting(blog, saveWriting, LocalDateTime.now(), tags, url);
        blogWritingRepository.save(blogWriting);

        //when
        final List<BlogWriting> blogWritings = blogWritingRepository.findByWritingId(writing.getId());

        //then
        assertThat(blogWritings).hasSize(1);
        assertThat(blogWritings.get(0)).usingRecursiveAssertion().ignoringFields("id").isEqualTo(blogWriting);
    }

    @Test
    @DisplayName("주어진 글의 발행정보를 모두 불러오는 테스트")
    void findWithFetch() {
        //given
        final Blog blog = new Blog(MEDIUM);
        final Member member = memberRepository.save(beaver_have_not_id);
        final Category category = new Category(new CategoryName("카테고리"), null, member);
        final Writing writing1 = Writing.of(member, new Title("Title 1"), category, new ArrayList<>());
        final Writing writing2 = Writing.of(member, new Title("Title 2"), category, new ArrayList<>());

        blogRepository.save(blog);
        categoryRepository.save(category);
        final Writing saveWriting1 = writingRepository.save(writing1);
        final Writing saveWriting2 = writingRepository.save(writing2);
        final BlogWriting blogWriting1 = new BlogWriting(blog, saveWriting1, LocalDateTime.now(), List.of("안녕"), "https://donggle.blog1");
        final BlogWriting blogWriting2 = new BlogWriting(blog, saveWriting2, LocalDateTime.now(), List.of("안녕"), "https://donggle.blog2");
        blogWritingRepository.save(blogWriting1);
        blogWritingRepository.save(blogWriting2);

        //when
        final List<BlogWriting> blogWritings = blogWritingRepository.findWithFetch(List.of(saveWriting1, saveWriting2));

        //then
        assertThat(blogWritings).hasSize(2);
    }
}
