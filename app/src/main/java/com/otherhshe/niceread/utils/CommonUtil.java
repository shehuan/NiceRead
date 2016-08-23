package com.otherhshe.niceread.utils;

import android.content.Context;

/**
 * Author: Othershe
 * Time: 2016/8/23 21:56
 */
public class CommonUtil {
    public static int getStatusBarHeight(Context context) {
        int result;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        result = context.getResources().getDimensionPixelSize(resourceId);
        if (result <= 0) {
            result = 25;
        }
        return result;
    }
}
