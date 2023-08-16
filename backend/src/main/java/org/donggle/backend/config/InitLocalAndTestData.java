package org.donggle.backend.config;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.BlockRepository;
import org.donggle.backend.application.repository.BlogRepository;
import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.domain.blog.Blog;
import org.donggle.backend.domain.blog.BlogType;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberName;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleRange;
import org.donggle.backend.domain.writing.StyleType;
import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.content.Block;
import org.donggle.backend.domain.writing.content.Depth;
import org.donggle.backend.domain.writing.content.NormalBlock;
import org.donggle.backend.domain.writing.content.RawText;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Profile({"local", "test"})
@RequiredArgsConstructor
public class InitLocalAndTestData implements CommandLineRunner {
    private final InitService initService;

    @Override
    public void run(final String... args) {
        initService.init();
    }

    @Component
    @RequiredArgsConstructor
    public static class InitService {
        private final BlogRepository blogRepository;
        private final MemberRepository memberRepository;
        private final WritingRepository writingRepository;
        private final BlockRepository blockRepository;
        private final CategoryRepository categoryRepository;

        @Transactional
        public void init() {
            final Member savedMember = memberRepository.save(Member.createByKakao(new MemberName("동그리"), 1L));

            final Category savedCategory = categoryRepository.save(Category.basic(savedMember));

            blogRepository.save(new Blog(BlogType.MEDIUM));
            blogRepository.save(new Blog(BlogType.TISTORY));

            final Writing savedWriting = writingRepository.save(Writing.lastOf(
                    savedMember,
                    new Title("테스트 글"),
                    savedCategory
            ));

            final Block savedBlock = blockRepository.save(
                    new NormalBlock(
                            savedWriting,
                            Depth.from(1),
                            BlockType.PARAGRAPH,
                            RawText.from("테스트 글입니다."),
                            List.of(new Style(new StyleRange(0, 2), StyleType.BOLD)
                            )
                    )
            );
        }
    }
}
