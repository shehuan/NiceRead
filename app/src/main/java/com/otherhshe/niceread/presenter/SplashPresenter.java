package com.otherhshe.niceread.presenter;

import com.otherhshe.niceread.data.SplashData;
import com.otherhshe.niceread.model.impl.SplashModelImpl;
import com.otherhshe.niceread.rx.RxManager;
import com.otherhshe.niceread.ui.view.SplashView;

import rx.Subscriber;

/**
 * Author: Othershe
 * Time:  2016/8/11 11:26
 */
public class SplashPresenter extends BasePresenter<SplashView> {
    private SplashModelImpl mModel;

    public SplashPresenter() {
        mModel = new SplashModelImpl();
    }

    public void getSplashPic() {
        mSubscription = RxManager.getInstance().doSubscribe(mModel.getSplashPic(), new Subscriber<SplashData>() {
            @Override
            public void onCompleted() {
                mView.loadComplete();
            }

            @Override
            public void onError(Throwable e) {
                mView.loadError();
            }

            @Override
            public void onNext(SplashData data) {
                mView.onSuccess(data);
            }
        });
    }
}
