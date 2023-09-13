package org.donggle.backend.application.service;

import jakarta.transaction.Transactional;
import org.donggle.backend.application.repository.BlogRepository;
import org.donggle.backend.application.repository.BlogWritingRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.blog.BlogService;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.domain.blog.Blog;
import org.donggle.backend.domain.blog.BlogType;
import org.donggle.backend.domain.blog.BlogWriting;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.exception.business.WritingAlreadyPublishedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class PublishServiceTest {
    @Autowired
    private BlogService blogService;
    @Autowired
    private WritingRepository writingRepository;
    @Autowired
    private BlogWritingRepository blogWritingRepository;
    @Autowired
    private BlogRepository blogRepository;

    @Test
    @DisplayName("중복 발행 금지 예외")
    void alreadyPublishedException() {
        //given
        final PublishRequest medium = new PublishRequest("MEDIUM", null);
        final Blog blog = blogRepository.findByBlogType(BlogType.MEDIUM).orElseThrow();
        final Writing writing = writingRepository.findById(1L).orElseThrow();
        blogWritingRepository.save(new BlogWriting(blog, writing, LocalDateTime.now(), null));


        //when
        //then
        assertThatThrownBy(() -> blogService.publishWriting(1L, 1L, medium)).isInstanceOf(WritingAlreadyPublishedException.class).hasMessageContaining("이미 발행된 글입니다.");
    }
}
