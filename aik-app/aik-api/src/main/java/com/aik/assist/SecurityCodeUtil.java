package com.aik.assist;

import java.util.Random;

/**
 * Description:
 * Created by as on 2017/8/3.
 */
public class SecurityCodeUtil {

    private static Random random = new Random();

    public static String generatorCode() {
        return 100000 + random.nextInt(899999) + "";
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++)
            System.out.println(generatorCode());
    }
}
