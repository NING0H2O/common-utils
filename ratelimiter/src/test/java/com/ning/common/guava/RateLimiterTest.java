package com.ning.common.guava;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Guava RateLimiter
 *
 * @author ning
 * @date 2021/5/9 17:03
 **/
@Slf4j
public class RateLimiterTest {

    @Test
    public void acquireTest() {
        RateLimiter rateLimiter = RateLimiter.create(1);
        long startTime = System.currentTimeMillis();
        IntStream.range(0, 10).forEach(i -> {
            // 在获取一个许可之前，会一直阻塞
            rateLimiter.acquire();
        });
        assertThat(System.currentTimeMillis() - startTime >= 9000).isTrue();
    }

    @Test
    public void tryAcquireTest() throws InterruptedException {
        RateLimiter rateLimiter = RateLimiter.create(0.2);

        new Thread(() -> {
            assertThat(rateLimiter.tryAcquire()).isTrue();
            doSomething_cost_5s();
        }).start();

        new Thread(() -> {
            // 不阻塞，立即返回
            assertThat(rateLimiter.tryAcquire()).isFalse();
        }).start();

        new Thread(() -> {
            assertThat(rateLimiter.tryAcquire(1, 3, TimeUnit.SECONDS)).isFalse();
        }).start();

        new Thread(() -> {
            // 在不超过指定Timeout的时间内获取到许可
            assertThat(rateLimiter.tryAcquire(1, 5, TimeUnit.SECONDS)).isTrue();
        }).start();

        Thread.currentThread().join(10_000);
    }


    public void doSomething_cost_5s() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
