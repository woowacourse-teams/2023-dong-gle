package org.donggle.backend.application.repository;

import org.donggle.backend.domain.blog.Blog;
import org.donggle.backend.domain.encryption.AESEncryptionUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.donggle.backend.domain.blog.BlogType.MEDIUM;
import static org.donggle.backend.domain.blog.BlogType.TISTORY;

@DataJpaTest
class BlogRepositoryTest {

    @Autowired
    private BlogRepository blogRepository;
    @MockBean
    private AESEncryptionUtil aesEncryptionUtil;

    @Test
    @DisplayName("blogType의 맞는 blog를 조회한다.")
    void findByBlogType() {
        //given
        final Blog tistoryBlog = blogRepository.save(new Blog(TISTORY));
        final Blog mediumBlog = blogRepository.save(new Blog(MEDIUM));

        //when
        final Optional<Blog> resultTistory = blogRepository.findByBlogType(TISTORY);
        final Optional<Blog> resultMedium = blogRepository.findByBlogType(MEDIUM);

        //then
        assertThat(resultTistory.get()).usingRecursiveComparison().isEqualTo(tistoryBlog);
        assertThat(resultMedium.get()).usingRecursiveComparison().isEqualTo(mediumBlog);
    }
}