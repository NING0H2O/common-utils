package com.ning.common.ratelimiter;

import com.ning.common.annotation.RateLimiter;
import com.ning.common.constant.LimitPolicy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ning
 * @date 2021/5/9 21:59
 **/
@Slf4j
@Service
public class LimitableService {

    @RateLimiter(qps = "${limitableService.doSomething:1}")
    public void doSomething_qps1_always_await() {
        log.info("do something...");
    }

    @RateLimiter(qps = "0.5", timeout = 1, limitPolicy = LimitPolicy.TIMEOUT_AWAIT)
    public void doSomething_qps05_timeout1_await() {
        log.info("do something...");
    }

}
