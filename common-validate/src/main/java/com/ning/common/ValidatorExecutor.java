package com.ning.common;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

/**
 * 校验器执行器
 *
 * @author ning
 * @date 2021/5/12 21:33
 **/
@Slf4j
public class ValidatorExecutor<T> {

    private List<Validator<T>> validatorList;

    private ValidatorExecutor() {
        validatorList = new LinkedList<>();
    }

    public static <T> ValidatorExecutor<T> init() {
        return new ValidatorExecutor<>();
    }

    public ValidatorExecutor<T> addValidator(Validator<T> validator) {
        validatorList.add(validator);
        return this;
    }

    public ValidatorExecutor<T> addAllValidator(List<Validator<T>> validatorList) {
        this.validatorList.addAll(validatorList);
        return this;
    }

    public ValidateResult execute(T target) {
        ValidateResult validateResult = null;
        for (Validator<T> validator : validatorList) {
            validateResult = validator.validate(target);
            if (!validateResult.isSuccess()) {
                return validateResult;
            }
        }
        return validateResult;
    }
}
