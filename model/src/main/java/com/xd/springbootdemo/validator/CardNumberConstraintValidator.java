package com.xd.springbootdemo.validator;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CardNumberConstraintValidator implements ConstraintValidator<CardNumberValidator, String> {

    @Override
    public void initialize(CardNumberValidator constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //为什么不用split方法？原因在于该方法使用了正则表达式
        //其次是NPE保护不够
        //如果在依赖中没有String.delimitedListToStringArray API的话，可以使用
        //StringTokenizer(JDK的api)
        //Apache commons-lang StringUtils
        String[] parts = StringUtils.split(value, "-");
        if (parts.length < 2) {
            return false;
        }
        if (ArrayUtils.getLength(parts) != 2) {
            return false;
        }

        String prefix = parts[0];
        String suffix = parts[1];

        boolean isValidPrefix = Objects.equals(prefix, "001");
        boolean isValidInteger = StringUtils.isNumeric(suffix);


        return isValidPrefix && isValidInteger;
    }
}
