package com.aik.util;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Description
 * @Author as
 * @Date 2017/7/26.
 */
public class DateUtilsTest {

    @Test
    public void jodaTimeTest() {
        DateTime dateTime = new DateTime();
        System.out.println(dateTime);
        Assert.assertNotNull(dateTime);
    }
}
