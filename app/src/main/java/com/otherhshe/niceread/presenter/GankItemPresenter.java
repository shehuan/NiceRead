package com.otherhshe.niceread.presenter;

import com.otherhshe.niceread.data.GankItemData;
import com.otherhshe.niceread.model.impl.GankItemModelImpl;
import com.otherhshe.niceread.rx.RxManager;
import com.otherhshe.niceread.rx.RxSubscriber;
import com.otherhshe.niceread.ui.view.GankItemView;

import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:29
 */
public class GankItemPresenter extends BasePresenter<GankItemView> {
    private GankItemModelImpl mModel;

    public GankItemPresenter(String suburl) {
        mModel = new GankItemModelImpl(suburl);
    }

    public void getGankItemData() {
        mSubscription = RxManager.getInstance().doSubscribe1(mModel.getGankItemData(), new RxSubscriber<List<GankItemData>>(true) {
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
