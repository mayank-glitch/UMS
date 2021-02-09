package com.example.ums.util;

public class DateUtil {

    private DateUtil() {
    }

    private static String zone       = "Asia/Kolkata";
    private static String zoneOffset = "+05:30";

    public static Long currentTimeSec() {
        return System.currentTimeMillis() / 1000;
    }

}
