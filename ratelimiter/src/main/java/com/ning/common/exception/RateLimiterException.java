package com.ning.common.exception;

import org.slf4j.helpers.MessageFormatter;

/**
 * @author ning
 * @date 2021/5/9 21:37
 **/
public class RateLimiterException extends RuntimeException {

    public RateLimiterException(String messagePattern, Object... params) {
        super(MessageFormatter.arrayFormat(messagePattern, params).getMessage());
    }

}
