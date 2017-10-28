package com.aik.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * Created by as on 2017/8/6.
 */
public class StringValidUtils {

    /**
     * 验证手机号是否合格
     *
     * @param mobileNo 手机号
     * @return true：验证通过 false：验证失败
     */
    public static boolean validMobileNo(String mobileNo) {
        if (StringUtils.isBlank(mobileNo)) {
            return false;
        }

        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mobileNo);
        return m.matches();
    }

    /**
     * 验证密码是否符合要求
     *
     * @param password 密码
     * @return true：验证通过 false：验证失败
     */
    public static boolean validPassword(String password) {
        if (StringUtils.isBlank(password)) {
            return false;
        }

        String regExp = "^.{6,16}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 验证支付密码是否符合要求
     *
     * @param password 密码
     * @return true：验证通过 false：验证失败
     */
    public static boolean validPayPassword(String password) {
        if (StringUtils.isBlank(password)) {
            return false;
        }

        String regExp = "^\\d{6}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 验证用户名
     *
     * @param userName 用户名
     * @return true：验证通过 false：验证失败
     */
    public static boolean validUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            return false;
        }

        String regExp = "^[a-zA-Z0-9_]{4,20}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(userName);
        return m.matches();
    }
}
