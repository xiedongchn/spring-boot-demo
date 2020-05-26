package com.xd.springbootdemo.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidCardNumberConstraintValidator implements ConstraintValidator<CardNumberValidator, String> {

    @Override
    public void initialize(CardNumberValidator constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //为什么不用split方法？原因在于该方法使用了正则表达式
        //其次是NPE保护不够
        //如果在依赖中没有String.delimitedListToStringArray API的话，可以使用
        //StringTokenizer
        String[] parts = StringUtils.delimitedListToStringArray(value, "-");
        if (parts.length < 2) {
            return false;
        }



        return false;
    }
}
