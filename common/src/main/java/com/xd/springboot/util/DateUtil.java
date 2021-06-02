package com.xd.springboot.util;

import java.text.DateFormat;
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
     * @param str        日期字符串
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

    public static int compareStrDate(String date1, String date2) {
        String formatStyle = "yyyy-MM-dd";
        date2 = date2 == null ? DateUtil.getCurrentDate() : date2;
        DateFormat df = new SimpleDateFormat(formatStyle);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(date1));
            c2.setTime(df.parse(date2));
        } catch (Exception e3) {
            System.out.println("wrong occured");
        }
        if (c1.after(c2)) {
            return 1;
        } else if (c1.before(c2)) {
            return -1;
        } else {
            return 0;
        }
    }
}
