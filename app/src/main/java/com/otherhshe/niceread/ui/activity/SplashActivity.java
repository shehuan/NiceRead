package com.otherhshe.niceread.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.widget.ImageView;

import com.otherhshe.niceread.R;
import com.otherhshe.niceread.data.SplashData;
import com.otherhshe.niceread.presenter.SplashPresenter;
import com.otherhshe.niceread.ui.view.SplashView;
import com.otherhshe.niceread.utils.ImageLoader;

import butterknife.BindView;

/**
 * Author: Othershe
 * Time:  2016/8/11 11:22
 */
public class SplashActivity extends BaseMvpActivity<SplashView, SplashPresenter> implements SplashView {
    @BindView(R.id.splash_iv)
    ImageView mSplashIv;

    @Override
    protected SplashPresenter initPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.getSplashPic();
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onSuccess(SplashData data) {
        ImageLoader.load(mContext, data.getUrl(), mSplashIv);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    @Override
    public void loadStart() {

    }

    @Override
    public void loadError() {

    }

    @Override
    public void loadComplete() {

    }
}
