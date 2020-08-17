package com.xd.springbootdemo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author xd
 * Created on 2020/8/17
 */
public class DateUtil {
    public static final String defaultFormat = "yyyy-MM-dd";

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat simple = new SimpleDateFormat(defaultFormat);
        return simple.format(date);
    }

    /**
     * 日期字符串转Date
     *
     * @param str 日期字符串
     * @param dateFormat 日期格式
     * @return Date
     */
    public static Date stringToDate(String str, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
