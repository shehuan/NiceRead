package com.otherhshe.niceread.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author: Othershe
 * Time:  2016/8/11 10:35
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    protected Unbinder mUnbinder;

    protected abstract int initLayoutId();

    protected abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initLayoutId();
        super.onCreate(savedInstanceState);
        setContentView(initLayoutId());

        mContext = this;
        mUnbinder = ButterKnife.bind(this);

        initView();
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
