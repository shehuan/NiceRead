package com.otherhshe.niceread.ui.fragemnt;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.otherhshe.niceread.R;
import com.otherhshe.niceread.data.GankItemData;
import com.otherhshe.niceread.presenter.GankItemPresenter;
import com.otherhshe.niceread.ui.view.GankItemView;

import java.util.List;

import butterknife.BindView;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:28
 */
public class GankItemFragment extends BaseMvpFragment<GankItemView, GankItemPresenter> implements GankItemView {

    private static final String SUB_TYPE = "subtype";

    private String mSubtype;

    @BindView(R.id.type_item_recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected GankItemPresenter initPresenter() {
        return new GankItemPresenter("data/Android/10/1");
    }

    @Override
    protected void fetchData() {
        mPresenter.getGankItemData();
        Log.e("type", mSubtype);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_type_item_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        if (getArguments() == null) {
            return;
        }
        mSubtype = getArguments().getString(SUB_TYPE);
    }

    @Override
    public void onSuccess(List<GankItemData> data) {

    }

    @Override
    public void onError() {

    }

    public static GankItemFragment newInstance(String subtype) {
        GankItemFragment fragment = new GankItemFragment();
        Bundle arguments = new Bundle();
        arguments.putString(SUB_TYPE, subtype);
        fragment.setArguments(arguments);
        return fragment;
    }
}
