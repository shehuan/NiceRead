package com.otherhshe.niceread.presenter;

import com.otherhshe.niceread.api.GirlService;
import com.otherhshe.niceread.net.ApiService;
import com.otherhshe.niceread.rx.RxManager;
import com.otherhshe.niceread.rx.RxSubscriber;
import com.otherhshe.niceread.ui.view.GirlDetailView;
import com.otherhshe.niceread.utils.JsoupUtil;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:29
 */
public class GirlDetailPresenter extends BasePresenter<GirlDetailView> {

    public GirlDetailPresenter(GirlDetailView view) {
        super(view);
    }

    public void getGirlDetailData(String id) {
        mSubscription = RxManager.getInstance().
                doSubscribe(ApiService.getInstance().initService(GirlService.class).getGirlDetailData(id),
                        new RxSubscriber<String>(false) {
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
