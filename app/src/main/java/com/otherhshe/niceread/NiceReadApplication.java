package com.otherhshe.niceread;

import android.app.Application;
import android.content.Context;

/**
 * Author: Othershe
 * Time:  2016/8/11 10:37
 */
public class NiceReadApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
