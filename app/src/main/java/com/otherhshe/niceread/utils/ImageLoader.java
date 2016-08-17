package com.otherhshe.niceread.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Author: Othershe
 * Time:  2016/8/11 14:47
 */
public class ImageLoader {
    public static void load(Context context, String url, ImageView iv) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(iv);
    }

    public static void load(Context context, int resId, ImageView iv) {
        Glide.with(context)
                .load(resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(iv);
    }
}
