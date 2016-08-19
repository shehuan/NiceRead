package com.otherhshe.niceread.sevice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;

import com.otherhshe.niceread.data.GirlItemData;
import com.otherhshe.niceread.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Author: Othershe
 * Time: 2016/8/18 11:48
 */
public class DBService extends IntentService {
    public DBService() {
        super("");
    }

    public static void startService(Context context, List<GirlItemData> datas) {
        Intent intent = new Intent(context, DBService.class);
        intent.putParcelableArrayListExtra("image", (ArrayList<? extends Parcelable>) datas);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        List<GirlItemData> datas = intent.getParcelableArrayListExtra("image");
        for (GirlItemData data : datas) {
            Bitmap bitmap = ImageLoader.load(this, data.getUrl());
            if (bitmap != null) {
                data.setWidth(bitmap.getWidth());
                data.setHeight(bitmap.getHeight());
            }
        }

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(datas);
        realm.commitTransaction();

    }
}
