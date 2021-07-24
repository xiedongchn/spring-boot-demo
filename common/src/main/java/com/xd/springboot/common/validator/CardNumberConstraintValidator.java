package com.xd.springboot.common.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

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
        assert parts != null;
        if (parts.length < 2) {
            return false;
        }
        if (parts.length != 2) {
            return false;
        }

        String prefix = parts[0];
        String suffix = parts[1];

        boolean isValidPrefix = Objects.equals(prefix, "001");
        boolean isValidInteger = isNumeric1(suffix);
        return isValidPrefix && isValidInteger;
    }

    //方法一：用JAVA自带的函数
    public static boolean isNumeric1(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /*方法二：推荐，速度最快
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */

    public static boolean isNumeric2(String str) {
        Pattern pattern = compile("^[-+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    //方法三：
    public static boolean isNumeric3(String str){
        Pattern pattern = compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    //方法四：
    public static boolean isNumeric4(String s) {
        if (s != null && !"".equals(s.trim())) {
            return s.matches("^[0-9]*$");
        } else {
            return false;
        }
    }

    //方法五：用ascii码
    public static boolean isNumeric5(String str){
        for(int i=str.length();--i>=0;){
            int chr=str.charAt(i);
            if(chr<48 || chr>57) {
                return false;
            }
        }
        return true;
    }
}
