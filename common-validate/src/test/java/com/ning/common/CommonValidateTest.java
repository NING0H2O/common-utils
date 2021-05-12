package com.ning.common;

import com.ning.common.validator.NameValidator;
import com.ning.common.validator.SizeValidator;

/**
 * @author ning
 * @date 2021/5/12 22:07
 **/
public class CommonValidateTest {

    public static void main(String[] args) {
        ValidateResult validateResult = ValidatorExecutor.<Object>init()
                .addValidator(new SizeValidator())
                .addValidator(new NameValidator())
                .addValidator((object) -> {
                    // do something check
                    return ValidateResult.fail("some error message");
                })
                .execute(new Object());
        System.out.println(validateResult.isSuccess());
        System.out.println(validateResult.getMessage());
    }
}
