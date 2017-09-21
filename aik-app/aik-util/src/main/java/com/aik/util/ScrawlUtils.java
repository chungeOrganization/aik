package com.aik.util;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Description:
 * Created by as on 2017/8/12.
 */
public class ScrawlUtils {

    public static int getAgeFromBirthday(Date birthday) {
        if (null == birthday) {
            return 0;
        }

        DateTime a = new DateTime(birthday.getTime());
        DateTime b = new DateTime();
        int age = b.getYear() - a.getYear();
        if (b.getMonthOfYear() > a.getMonthOfYear()) {
            age++;
        } else if (b.getMonthOfYear() == a.getMonthOfYear() &&
                b.getDayOfMonth() >= a.getDayOfMonth()) {
            age++;
        }
        return age;
    }

    public static void main(String[] args) {
        DateTime dateTime = new DateTime("2016-08-13");
        Date birthday = dateTime.toDate();
        System.out.println(getAgeFromBirthday(birthday));
    }
}
