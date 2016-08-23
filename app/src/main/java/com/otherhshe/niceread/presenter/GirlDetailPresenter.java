package com.otherhshe.niceread.presenter;

import com.otherhshe.niceread.model.IGirlDetailModel;
import com.otherhshe.niceread.model.impl.GirlDetailModelImpl;
import com.otherhshe.niceread.rx.RxManager;
import com.otherhshe.niceread.rx.RxSubscriber;
import com.otherhshe.niceread.ui.view.GirlDetailView;
import com.otherhshe.niceread.utils.JsoupUtil;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:29
 */
public class GirlDetailPresenter extends BasePresenter<GirlDetailView> {
    private IGirlDetailModel mModel;

    public GirlDetailPresenter() {
        mModel = new GirlDetailModelImpl();
    }

    public void getGirlDetailData(String id) {
        mSubscription = RxManager.getInstance().doSubscribe(mModel.getGirlDetailData(id), new RxSubscriber<String>(false) {
            @Override
            protected void _onNext(String s) {
                mView.onSuccess(JsoupUtil.parseGirlDetail(s));
            }

            @Override
            protected void _onError() {
                mView.onError();
            }
        });
    }
}
