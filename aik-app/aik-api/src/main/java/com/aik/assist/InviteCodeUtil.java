package com.aik.assist;

import java.util.Random;

/**
 * Description:
 * Created by as on 2017/8/6.
 */
public class InviteCodeUtil {

    private static Random random = new Random();

    public static String generatorCode() {
        return 100000 + random.nextInt(899999) + "";
    }
}
