package com.otherhshe.niceread.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Author: Othershe
 * Time: 2016/8/16 17:09
 */
public class CopyUtil {
    public static void copy(Context context, String plaintText) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText(null, plaintText));
    }
}
