package com.aik.util;

import java.security.MessageDigest;

/**
 * Description:
 * Created by as on 2017/8/6.
 */
public class MD5Utils {

    /**
     * 使用md5的算法进行加密
     */
    public static String md5(String src) {
        try {
            StringBuilder buffer = new StringBuilder();
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            char[] ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'A', 'B', 'C', 'D', 'E', 'F'};
            byte[] bytes = messageDigest.digest(src.getBytes());

            for (byte b: bytes) {
                // 将高4位转换成字符串
                int x = (b >>> 4 & 0x0f);
                buffer.append(ch[x]);
                // 将低4位转换成字符串
                x = (b & 0x0f);
                buffer.append(ch[x]);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
