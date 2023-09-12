package org.donggle.backend.application.service.support;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.BlogRepository;
import org.donggle.backend.domain.blog.Blog;
import org.donggle.backend.domain.blog.BlogType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile({"dev"})
@RequiredArgsConstructor
public class InitDevData implements CommandLineRunner {
    private final InitService initService;

    @Override
    public void run(final String... args) {
        initService.init();
    }

    @Component
    @RequiredArgsConstructor
    public static class InitService {
        private final BlogRepository blogRepository;

        @Transactional
        public void init() {
            blogRepository.save(new Blog(BlogType.MEDIUM));
            blogRepository.save(new Blog(BlogType.TISTORY));
        }
    }
}
