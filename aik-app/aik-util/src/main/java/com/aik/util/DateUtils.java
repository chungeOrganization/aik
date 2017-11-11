package com.aik.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DurationFieldType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    /**
     * aik项目个性化时间显示
     *
     * @param date 时间
     * @return 个性化时间
     */
    public static String aikPersonaliseDate(Date date) {
        if (null == date) {
            return "";
        }

        DateTime dateTime = new DateTime(date.getTime());
        DateTime now = new DateTime();
        if (dateTime.toLocalDate().equals(now.toLocalDate())) {
            return dateTime.toLocalTime().toString("HH:mm");
        } else if (dateTime.withFieldAdded(DurationFieldType.days(), 1).toLocalDate().equals(
                now.toLocalDate())) {
            return "昨天";
        } else {
            return dateTime.toLocalDate().toString();
        }
    }

    /**
     * aik项目优惠券个性化时间显示
     *
     * @param date 时间
     * @return 个性化时间
     */
    public static String aikCouponDate(String date) {
        if (StringUtils.isBlank(date)) {
            return "";
        }

        return DateTime.parse(date, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S")).toString("M.dd");
    }

    public static String showDate(Date date) {
        if (null == date) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }

    public static String showDate(Date date, String pattern) {
        if (null == date) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf.format(date);
    }

    public static Date parseDate(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            // ignore
        }
        return date;
    }

    public static void main(String[] args) {
//        DateTime dateTime = new DateTime();
//        dateTime = dateTime.withFieldAdded(DurationFieldType.days(), -2);
//        System.out.println(aikPersonaliseDate(dateTime.toDate()));

//        System.out.println(aikCouponDate("2017-11-22 11:22:11"));
        double a = 20.0/3;
        System.out.println(a);
    }
}
