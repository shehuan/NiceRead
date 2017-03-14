package com.otherhshe.niceread.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.widget.ImageView;

import com.otherhshe.niceread.R;
import com.otherhshe.niceread.model.SplashData;
import com.otherhshe.niceread.presenter.SplashPresenter;
import com.otherhshe.niceread.ui.fragemnt.SetFragment;
import com.otherhshe.niceread.ui.view.SplashView;
import com.otherhshe.niceread.utils.DateUtil;
import com.otherhshe.niceread.utils.ImageLoader;
import com.otherhshe.niceread.utils.NetUtil;
import com.otherhshe.niceread.utils.SPUtil;

import butterknife.BindView;

/**
 * Author: Othershe
 * Time:  2016/8/11 11:22
 */
public class SplashActivity extends BaseMvpActivity implements SplashView {
    private String mTimeLine;

    @BindView(R.id.splash_iv)
    ImageView mSplashIv;

    @Override
    protected void fetchData() {
        if (!DateUtil.formatDate().equals(mTimeLine)) {
            SplashPresenter presenter = new SplashPresenter(this);
            presenter.getSplashPic();
            addPresenter(presenter);
        }
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        if (!(Boolean) SPUtil.get(SetFragment.SPLASH, false) || !NetUtil.isConnected(mContext)) {
            ImageLoader.load(mContext, R.drawable.original_splash, mSplashIv);
        } else {
            ImageLoader.load(mContext, (String) SPUtil.get("splash_url", ""), mSplashIv);
        }

        startDelay();
    }

    @Override
    protected void initData() {
        mTimeLine = (String) SPUtil.get("splash_time", "");
    }

    @Override
    public void onSuccess(SplashData data) {
        SPUtil.save("splash_time", DateUtil.formatDate());
        SPUtil.save("splash_url", data.getUrl());
    }

    @Override
    public void onError() {

    }

    private void startDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
