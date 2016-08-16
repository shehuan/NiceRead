package com.otherhshe.niceread.utils;

import android.content.Context;
import android.content.Intent;

import com.otherhshe.niceread.R;

/**
 * Author: Othershe
 * Time: 2016/8/16 17:01
 */
public class ShareUtil {
    public static void share(Context context, String plainContent) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, plainContent);
        intent.setType("text/plain");

        context.startActivity(Intent.createChooser(intent, ResourceUtil.resToStr(context, R.string.share_to)));
    }
}
