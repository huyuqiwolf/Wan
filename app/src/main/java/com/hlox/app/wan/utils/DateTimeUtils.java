package com.hlox.app.wan.utils;

import java.text.SimpleDateFormat;

public class DateTimeUtils {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String format(long time){
        return format.format(time);
    }
}
