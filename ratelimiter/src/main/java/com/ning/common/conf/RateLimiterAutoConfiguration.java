package com.ning.common.conf;

import com.ning.common.aspect.RateLimiterAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ning
 * @date 2021/5/9 16:33
 **/
@Configuration
public class RateLimiterAutoConfiguration {

    @Autowired
    private ConfigurableBeanFactory beanFactory;

    @Bean
    public RateLimiterAspect rateLimiterAspect() {
        return new RateLimiterAspect(beanFactory);
    }
}
