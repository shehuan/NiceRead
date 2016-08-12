package com.otherhshe.niceread.ui.fragemnt;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.otherhshe.niceread.ui.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author: Othershe
 * Time:  2016/8/11 10:41
 */
public abstract class BaseFragment extends Fragment {
    protected BaseActivity mActivity;
    protected View mRootView;
    protected Unbinder mUnbinder;

    protected abstract int initLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initLayoutId();
        mRootView = inflater.inflate(initLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        initView();
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }
}
