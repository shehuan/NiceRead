package com.otherhshe.niceread.ui.fragemnt;

import android.os.Bundle;

import com.otherhshe.niceread.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/12 12:19
 */
public abstract class BaseMvpFragment extends BaseFragment {
    protected static final String SUB_TYPE = "subtype";

    protected List<BasePresenter> mPresenters = new ArrayList<>();

    protected abstract void fetchData();

    protected boolean mIsViewInitiated;
    protected boolean mIsVisibleToUser;
    protected boolean mIsDataInitiated;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
        initFetchData();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mIsViewInitiated = true;
        initFetchData();
    }

    protected void initFetchData() {
        if (mIsVisibleToUser && mIsViewInitiated && !mIsDataInitiated) {
            fetchData();
            mIsDataInitiated = true;
        }
    }

    protected void addPresenter(BasePresenter presenter) {
        mPresenters.add(presenter);
    }

    @Override
    public void onDestroy() {
        for (BasePresenter p : mPresenters) {
            p.detach();
        }
        super.onDestroy();
    }
}
