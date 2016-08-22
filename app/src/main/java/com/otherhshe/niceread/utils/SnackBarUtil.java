package com.otherhshe.niceread.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Author: Othershe
 * Time: 2016/8/16 17:01
 */
public class SnackBarUtil {

    public static void show(View rootView, int textId) {
        Snackbar.make(rootView, textId, Snackbar.LENGTH_SHORT).show();
    }

    public static void show(View rootView, String text) {
        Snackbar.make(rootView, text, Snackbar.LENGTH_SHORT).show();
    }
}
