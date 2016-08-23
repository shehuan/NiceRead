package com.otherhshe.niceread.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Author: Othershe
 * Time: 2016/8/23 10:28
 */
public class DateUtil {
    public static final String TYPE1 = "yyyy-MM-dd";

    public static String formatDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TYPE1, Locale.getDefault());
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}
