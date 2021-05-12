package com.ning.common;

import lombok.Getter;

/**
 * 校验器校验结果
 *
 * @author ning
 * @date 2021/5/12 21:37
 **/
@Getter
public class ValidateResult {

    private boolean success;
    private String message;

    public ValidateResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static ValidateResult fail(String message) {
        return new ValidateResult(false, message);
    }
}
