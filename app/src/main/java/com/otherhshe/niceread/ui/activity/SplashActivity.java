package com.otherhshe.niceread.ui.activity;

import com.otherhshe.niceread.R;
import com.otherhshe.niceread.presenter.SplashPresenter;
import com.otherhshe.niceread.ui.view.SplashView;

/**
 * Author: Othershe
 * Time:  2016/8/11 11:22
 */
public class SplashActivity extends BaseMvpActivity<SplashView, SplashPresenter> implements SplashView {
    @Override
    protected SplashPresenter initPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
