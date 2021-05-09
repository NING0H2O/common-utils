package com.ning.common.annotation;

import com.ning.common.constant.LimitPolicy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 限流注解
 *
 * @author ning
 * @date 2021/5/9 16:12
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimiter {

    /**
     * 限流QPS，支持EL表达式配置
     *
     * @return
     */
    String qps();

    /**
     * 限流Key
     * 未配置默认为方法签名
     * 同一个限流key使用同一个限流器
     *
     * @return
     */
    String rateLimiterKey() default "";

    /**
     * 限流策略
     * @see LimitPolicy
     *
     * @return
     */
    LimitPolicy limitPolicy() default LimitPolicy.ALWAYS_AWAIT;

    /**
     * 超时等待时间
     *
     * @return
     */
    int timeout() default 0;

    /**
     * 超时等待时间单位
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 被限流时提示的错误信息
     *
     * @return
     */
    String errorMsg() default "";
}
