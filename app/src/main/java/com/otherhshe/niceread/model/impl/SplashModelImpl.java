package com.otherhshe.niceread.model.impl;

import com.otherhshe.niceread.api.SplashService;
import com.otherhshe.niceread.data.SplashData;
import com.otherhshe.niceread.model.ISplashModel;
import com.otherhshe.niceread.net.NetManager;

import rx.Observable;

/**
 * Author: Othershe
 * Time:  2016/8/11 12:22
 */
public class SplashModelImpl implements ISplashModel {

    @Override
    public Observable<SplashData> getSplashPic() {
        SplashService service = NetManager.getInstance().create(SplashService.class);
        return service.getSplashPic();
    }
}
