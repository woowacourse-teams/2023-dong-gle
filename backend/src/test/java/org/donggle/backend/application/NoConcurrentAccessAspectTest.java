package org.donggle.backend.application;

import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.service.CategoryService;
import org.donggle.backend.application.service.request.CategoryAddRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
public class NoConcurrentAccessAspectTest {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MemberRepository memberRepository;

    private static final int THREAD_COUNT = 10;


    @Test
    @DisplayName("동시에 카테고리 추가 요청 테스트")
    void addCategoryConcurrently() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        IntStream.range(0, THREAD_COUNT).forEach(i -> executorService.submit(() -> {
            try {
                categoryService.addCategory(1L, new CategoryAddRequest("동시 카테고리 " + i));
            } finally {
                latch.countDown();
            }
        }));

        latch.await();

        final long categoryCount = categoryRepository.findAllByMemberId(1L).size();
        assertThat(categoryCount).isEqualTo(2);
    }

    @Test
    @DisplayName("동시에 두 멤버가 카테고리 추가 요청 테스트")
    void addCategoryConcurrentlyForBothMembers() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        IntStream.range(0, THREAD_COUNT).forEach(i -> executorService.submit(() -> {
            try {
                final Long memberId;
                if (i % 2 == 0) {
                    memberId = 1L;
                } else {
                    memberId = 2L;
                }

                categoryService.addCategory(memberId, new CategoryAddRequest("동시 카테고리 " + i));
            } finally {
                latch.countDown();
            }
        }));

        latch.await();
        final long categoryCountForMember1 = categoryRepository.findAllByMemberId(1L).size();
        final long categoryCountForMember2 = categoryRepository.findAllByMemberId(2L).size();

        assertThat(categoryCountForMember1).isEqualTo(2);
        assertThat(categoryCountForMember2).isEqualTo(2);
    }
}
