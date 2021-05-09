package com.ning.common;

import com.ning.common.ratelimiter.LimitableService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author ning
 * @date 2021/5/9 22:03
 **/
@SpringBootTest(classes = UtilsTestApplication.class)
@RunWith(SpringRunner.class)
public class RateLimiterTest {

    @Autowired
    private LimitableService service;

    @Test
    public void testRateLimiter_qps1_always_await() {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            service.doSomething_qps1_always_await();
        }

        assertThat(System.currentTimeMillis() - startTime >= 9000).isTrue();
    }

    // TODO
    @Test
    public void testRateLimiter_qps05_timout1_await() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    service.doSomething_qps05_timeout1_await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.currentThread().join();
    }
}
