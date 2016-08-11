package com.otherhshe.niceread.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Author: Othershe
 * Time:  2016/8/11 14:47
 */
public class ImageLoader {
    public static void load(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).into(iv);
    }
}
