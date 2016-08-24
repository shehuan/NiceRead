package com.otherhshe.niceread;

import android.app.Application;
import android.content.Context;

import com.otherhshe.niceread.utils.SPUtil;

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

        initRealm();
        SPUtil.init(mContext, "niceread");
    }

    public static Context getContext() {
        return mContext;
    }

    private void initRealm() {
//        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
//        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
