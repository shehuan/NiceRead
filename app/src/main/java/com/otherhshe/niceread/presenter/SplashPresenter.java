package com.otherhshe.niceread.presenter;

import com.otherhshe.niceread.api.SplashService;
import com.otherhshe.niceread.model.SplashData;
import com.otherhshe.niceread.net.ApiService;
import com.otherhshe.niceread.rx.RxManager;
import com.otherhshe.niceread.rx.RxSubscriber;
import com.otherhshe.niceread.ui.view.SplashView;

/**
 * Author: Othershe
 * Time:  2016/8/11 11:26
 */
public class SplashPresenter extends BasePresenter<SplashView> {

    public SplashPresenter(SplashView view) {
        super(view);
    }

    public void getSplashPic() {
        mSubscription = RxManager.getInstance()
                .doSubscribe(ApiService.getInstance().initService(SplashService.class).getSplashPic(),
                        new RxSubscriber<SplashData>(false) {
                            @Override
                            protected void _onNext(SplashData data) {
                                mView.onSuccess(data);
                            }

                            @Override
                            protected void _onError() {
                                mView.onError();
                            }
                        });
    }
}
