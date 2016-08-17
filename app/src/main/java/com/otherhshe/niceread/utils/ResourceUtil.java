package com.otherhshe.niceread.utils;

import android.content.Context;

import java.util.Arrays;
import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/12 15:34
 */
public class ResourceUtil {
    /**
     * 解析String 类型的arrays.xml元素
     *
     * @param context
     * @param arrayId
     * @return
     */
    public static List<String> stringArrayToList(Context context, int arrayId) {
        return Arrays.asList(context.getResources().getStringArray(arrayId));
    }

    public static String resToStr(Context context, int strId) {
        return context.getString(strId);
    }
}
