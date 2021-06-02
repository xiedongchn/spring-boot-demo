package com.xd.springboot.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Max;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 卡号合法性校验
 * 需求：通过用户的卡号来校验，前缀和后缀符合一定的规则
 */
@Target({FIELD})//可以放到什么上
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {CardNumberConstraintValidator.class})
public @interface CardNumberValidator {

    String message() default "com.xd.springboot.validator.message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return value the element must be lower or equal to
     */
    long value() default 0L;

    /**
     * Defines several {@link Max} annotations on the same element.
     *
     * @see Max
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Max[] value();
    }
}
