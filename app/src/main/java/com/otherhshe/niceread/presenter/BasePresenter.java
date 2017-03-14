package com.otherhshe.niceread.presenter;


import com.otherhshe.niceread.ui.view.IBaseView;

import rx.Subscription;

/**
 * Author: Othershe
 * Time:  2016/8/11 11:17
 */
public class BasePresenter<V extends IBaseView> {
    public V mView;
    protected Subscription mSubscription;

    public BasePresenter(V view){
        mView = view;
    }

    public void detach() {
        mView = null;
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }
}
