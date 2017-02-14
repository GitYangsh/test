package com.example.retrofit.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/2/14 16:07
 * Description:
 */

public class DateUtil {

    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        }
    };

    private static DateFormat getDateFormat() {
        return df.get();
    }

    public static String format(Date date){
        return getDateFormat().format(date);
    }
}
