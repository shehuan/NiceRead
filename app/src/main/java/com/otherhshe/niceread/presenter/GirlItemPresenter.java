package com.otherhshe.niceread.presenter;

import com.otherhshe.niceread.api.GirlService;
import com.otherhshe.niceread.net.ApiService;
import com.otherhshe.niceread.rx.RxManager;
import com.otherhshe.niceread.rx.RxSubscriber;
import com.otherhshe.niceread.ui.view.GirlItemView;
import com.otherhshe.niceread.utils.JsoupUtil;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:29
 */
public class GirlItemPresenter extends BasePresenter<GirlItemView> {

    public GirlItemPresenter(GirlItemView view) {
        super(view);
    }

    public void getGirlItemData(String cid, int page) {
        mSubscription = RxManager.getInstance()
                .doSubscribe(ApiService.getInstance().initService(GirlService.class).getGirlItemData(cid, page),
                        new RxSubscriber<String>(false) {
                            @Override
                            protected void _onNext(String s) {
                                mView.onSuccess(JsoupUtil.parseGirls(s));
                            }

                            @Override
                            protected void _onError() {
                                mView.onError();
                            }
                        });
    }
}
