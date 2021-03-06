package com.aik.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.Random;

/**
 * Description:
 * Created by as on 2017/8/12.
 */
public class ScrawlUtils {

    private static final int AIK_STRING_OMIT_MIN_LENGTH = 10;
    private static final String AIK_STRING_OMIT_SIGN = "......";

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

    public static String aikStringOmit(String content) {
        if (StringUtils.isBlank(content)) {
            return "";
        }

        if (content.length() <= AIK_STRING_OMIT_MIN_LENGTH) {
            return content;
        }

        return content.substring(0, 10) + AIK_STRING_OMIT_SIGN;
    }

    public static String bankCardShowHandle(String cardCode) {
        if (StringUtils.isBlank(cardCode)) {
            return "";
        }

        String hidStr = cardCode.substring(0, cardCode.length() - 4);
        hidStr = hidStr.replaceAll("[0-9]", "*");
        String showStr = cardCode.substring(cardCode.length() - 4, cardCode.length());

        return hidStr + showStr;
    }

    /**
     * 生成随机字符串
     *
     * @param size 长度
     * @return 随机字符串
     */
    public static String generateNonceStr(Integer size) {
        if (null == size || size <= 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        String toolStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Integer toolStrLength = toolStr.length();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            sb.append(toolStr.charAt(random.nextInt(toolStrLength)));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
//        DateTime dateTime = new DateTime("2016-08-13");
//        Date birthday = dateTime.toDate();
//        System.out.println(getAgeFromBirthday(birthday));
        System.out.println(bankCardShowHandle("12345678911"));
    }
}
