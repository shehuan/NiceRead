package com.otherhshe.niceread.presenter;

import com.otherhshe.niceread.model.IGirlItemModel;
import com.otherhshe.niceread.model.impl.GirlItemModelImpl;
import com.otherhshe.niceread.rx.RxManager;
import com.otherhshe.niceread.rx.RxSubscriber;
import com.otherhshe.niceread.ui.view.GirlItemView;
import com.otherhshe.niceread.utils.JsoupUtil;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:29
 */
public class GirlItemPresenter extends BasePresenter<GirlItemView> {
    private IGirlItemModel mModel;

    public GirlItemPresenter() {
        mModel = new GirlItemModelImpl();
    }

    public void getGirlItemData(String cid, int page) {
        mSubscription = RxManager.getInstance().doSubscribe(mModel.getGirlItemData(cid, page), new RxSubscriber<String>(false) {
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
