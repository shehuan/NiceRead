package com.otherhshe.niceread.presenter;


/**
 * Author: Othershe
 * Time:  2016/8/11 11:17
 */
public class BasePresenter<V> {
    public V mView;

    public void attach(V view) {
        mView = view;
    }

    public void detach() {
        mView = null;
    }
}
