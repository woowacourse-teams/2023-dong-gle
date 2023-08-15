package org.donggle.backend.application.service;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.donggle.backend.application.repository.BlogRepository;
import org.donggle.backend.application.repository.BlogWritingRepository;
import org.donggle.backend.application.repository.WritingRepository;
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

@SpringBootTest
@Transactional
class PublishServiceTest {
    @Autowired
    private PublishService publishService;
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
        final Blog blog = blogRepository.findByBlogType(BlogType.MEDIUM).orElseThrow();
        final Writing writing = writingRepository.findById(1L).orElseThrow();
        blogWritingRepository.save(new BlogWriting(blog, writing, LocalDateTime.now(), null));

        //when

        //then
        Assertions.assertThatThrownBy(() ->
                        publishService.publishWriting(1L, 1L, new PublishRequest("MEDIUM", null)))
                .isInstanceOf(WritingAlreadyPublishedException.class)
                .hasMessageContaining("이미 발행된 글입니다.");

    }
}