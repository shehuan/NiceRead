package com.otherhshe.niceread.presenter;

import com.otherhshe.niceread.api.GankService;
import com.otherhshe.niceread.model.GankItemData;
import com.otherhshe.niceread.net.ApiService;
import com.otherhshe.niceread.rx.RxManager;
import com.otherhshe.niceread.rx.RxSubscriber;
import com.otherhshe.niceread.ui.view.GankItemView;

import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:29
 */
public class GankItemPresenter extends BasePresenter<GankItemView> {
    public GankItemPresenter(GankItemView view) {
        super(view);
    }

    public void getGankItemData(String suburl) {
        mSubscription = RxManager.getInstance()
                .doSubscribe1(ApiService.getInstance().initService(GankService.class).getGankItemData(suburl),
                        new RxSubscriber<List<GankItemData>>(true) {
                            @Override
                            protected void _onNext(List<GankItemData> gankItemData) {
                                mView.onSuccess(gankItemData);
                            }

                            @Override
                            protected void _onError() {
                                mView.onError();
                            }
                        });
    }
}
