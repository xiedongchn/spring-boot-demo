package com.xd.springbootdemo.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 时间处理工具类
 */
public class DateExtUtils {
    private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private static final String DEFAULT_DATE_MONTH_PATTERN = "MM月dd日";
    private static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

    private static final DateTimeFormatter dtf;
    private static final DateTimeFormatter df;
    private static final DateTimeFormatter tf;
    private static final DateTimeFormatter dm;

    static {
        dtf = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN);
        df = DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);
        tf = DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN);
        dm = DateTimeFormatter.ofPattern(DEFAULT_DATE_MONTH_PATTERN);
    }

    /**
     * 获得当前日期 yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(dtf);
    }

    /**
     * 字符串时间与Date时间转换
     * @param dateStr yyyy-MM-dd HH:mm:ss
     */
    public static Date toDateTime(String dateStr) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, dtf);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date date(String dateString) {
        LocalDate localDate = LocalDate.parse(dateString, df);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取当前日期 yyyy-MM-dd
     */
    public static String getCurrentDate() {
        return LocalDate.now().format(df);
    }

    /**
     * 获取当前时间 HH:mm:ss
     */
    public static String getCurrentTime() {
        return LocalTime.now().format(tf);
    }

    /**
     * 获得指定时间日期 yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime().format(dtf);
    }

    /**
     * 获取指定时间日期 yyyy-MM-dd
     */
    public static String getDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime().format(df);
    }

    /**
     * 获取指定时间日期 yyyy-MM-dd
     */
    public static String getDateFromDateTime(String dateTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dtf);
        return localDateTime.format(df);
    }

    /**
     * 获取指定时间日期:MM月dd日
     */
    public static String getDateMonthFromDate(String date) {
        LocalDate localDate = LocalDate.parse(date, df);
        return localDate.format(dm);
    }

    /**
     * 获取指定时间 HH:mm:ss
     */
    public static String getTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalTime().format(tf);
    }


    /**
     * 获取当天剩余秒数
     */
    public static Long getDayRemainSeconds(){
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return ChronoUnit.SECONDS.between(LocalDateTime.now(),midnight);
    }
}
