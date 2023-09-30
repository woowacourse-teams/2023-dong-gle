package org.donggle.backend.application.service.concurrent;

import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.service.category.CategoryService;
import org.donggle.backend.application.service.request.CategoryAddRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

//todo: 슬라이스 테스트로 바꾸기
@SpringBootTest
class NoConcurrentAccessAspectTest {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

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
}