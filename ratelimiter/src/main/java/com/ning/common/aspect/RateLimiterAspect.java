package com.ning.common.aspect;

import com.google.common.util.concurrent.RateLimiter;
import com.ning.common.exception.RateLimiterException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author ning
 * @date 2021/5/9 16:17
 **/
@Slf4j
@Aspect
public class RateLimiterAspect {

    /**
     * key: rateLimiterKey
     */
    private ConcurrentHashMap<String, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

    private ConfigurableBeanFactory beanFactory;

    public RateLimiterAspect(ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Pointcut("@annotation(com.ning.common.annotation.RateLimiter)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void executeBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        com.ning.common.annotation.RateLimiter annotation = signature.getMethod()
                .getAnnotation(com.ning.common.annotation.RateLimiter.class);
        String rateLimiterKey = StringUtils.isNotBlank(annotation.rateLimiterKey()) ? annotation.rateLimiterKey() :
                signature.getMethod().toGenericString();
        RateLimiter rateLimiter = rateLimiterMap.get(rateLimiterKey);
        if (Objects.isNull(rateLimiter)) {
            synchronized (rateLimiterKey.intern()) {
                if (Objects.isNull(rateLimiterMap.get(rateLimiterKey))) {
                    double qps = Double.parseDouble(beanFactory.resolveEmbeddedValue(annotation.qps()));
                    rateLimiter = RateLimiter.create(qps);
                    rateLimiterMap.put(rateLimiterKey, rateLimiter);
                    log.info("create a new RateLimiter. rateLimiterKey:{}, QPS:{}", rateLimiterKey, qps);
                } else {
                    rateLimiter = rateLimiterMap.get(rateLimiterKey);
                }
            }
        }

        switch (annotation.limitPolicy()) {
            case ALWAYS_AWAIT:
                rateLimiter.acquire();
                break;
            case TIMEOUT_AWAIT:
                if (!rateLimiter.tryAcquire(1, annotation.timeout(), annotation.timeUnit())) {
                    String qps = beanFactory.resolveEmbeddedValue(annotation.qps());
                    throw new RateLimiterException("{} execution speed over the QPS {}", rateLimiterKey, qps);
                }
                break;
        }
    }

}
