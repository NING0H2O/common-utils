package com.ning.common.validator;

import com.ning.common.ValidateResult;
import com.ning.common.Validator;

/**
 * @author ning
 * @date 2021/5/12 22:13
 **/
public class NameValidator implements Validator<Object> {
    @Override
    public ValidateResult validate(Object target) {

        return SUCCESS;
    }
}
