package com.otherhshe.niceread.presenter;

import com.otherhshe.niceread.data.SplashData;
import com.otherhshe.niceread.model.ISplashModel;
import com.otherhshe.niceread.model.impl.SplashModelImpl;
import com.otherhshe.niceread.rx.RxManager;
import com.otherhshe.niceread.rx.RxSubscriber;
import com.otherhshe.niceread.ui.view.SplashView;

/**
 * Author: Othershe
 * Time:  2016/8/11 11:26
 */
public class SplashPresenter extends BasePresenter<SplashView> {
    private ISplashModel mModel;

    public SplashPresenter() {
        mModel = new SplashModelImpl();
    }

    public void getSplashPic() {
        mSubscription = RxManager.getInstance().doSubscribe(mModel.getSplashPic(), new RxSubscriber<SplashData>(false) {
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
