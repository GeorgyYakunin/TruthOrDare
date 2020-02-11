package com.example.truthordare.utils;

public class StringUtils {
    public static boolean checkNull(Object o) {
        return o == null || o.toString().trim().length() == 0;
    }

    public static <T> T subNulls(T o) {
        return checkBlank(o, null);
    }

    public static <T> T checkBlank(T o, T defaultVal) {
        return checkNull(o) ? defaultVal : o;
    }

    public static Object checkBlank(String o, String defaultVal, String oprefix) {
        return checkNull(o) ? defaultVal : oprefix + o;
    }
}
