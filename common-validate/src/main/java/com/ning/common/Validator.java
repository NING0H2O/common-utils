package com.ning.common;

/**
 * 校验器
 *
 * @author ning
 * @date 2021/5/12 21:34
 **/
public interface Validator<T> {

    ValidateResult SUCCESS = new ValidateResult(true, "Success.");

    /**
     * 校验
     *
     * @param target
     * @return
     */
    ValidateResult validate(T target);
}
