package org.donggle.backend.application.service.category;

import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.service.request.CategoryAddRequest;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.category.CategoryName;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberName;
import org.donggle.backend.domain.oauth.SocialType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CategoryServiceTest {
    @Autowired
    private CategoryFacadeService categoryFacadeService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    private Member member;
    private Category basicCategory;

    @BeforeEach
    void setUp() {
        //given
        member = memberRepository.save(Member.of(
                new MemberName("테스트 멤버"),
                1234L,
                SocialType.KAKAO
        ));

        basicCategory = categoryRepository.save(Category.of(new CategoryName("기본"), member));
    }

    @Test
    void testAddCategoryConcurrency() throws InterruptedException {
        final int threads = 100;
        final ExecutorService executorService = Executors.newFixedThreadPool(20);
        final CountDownLatch latch = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {
            final int finalI = i;
            executorService.submit(() -> {
                try {
                    final CategoryAddRequest request = new CategoryAddRequest("TestCategory" + finalI);
                    final Long categoryId = categoryFacadeService.addCategory(member.getId(), request);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        final Category category = categoryRepository.findLastCategoryByMemberId(member.getId()).get();
        final int size = categoryRepository.findAllByMemberId(member.getId()).size();
        assertThat(size).isEqualTo(101);
    }
}
