package com.xd.springbootdemo.validator;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 卡号合法性校验
 * 需求：通过用户的卡号来校验，前缀和后缀符合一定的规则
 */
@Target({ FIELD })//可以放到什么上
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { })
public @interface CardNumberValidator {

}
