package com.otherhshe.niceread.ui.fragemnt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.otherhshe.niceread.R;
import com.otherhshe.niceread.data.GankItemData;
import com.otherhshe.niceread.presenter.GankItemPresenter;
import com.otherhshe.niceread.ui.activity.GankDetailActivity;
import com.otherhshe.niceread.ui.adapter.GankItemAdapter;
import com.otherhshe.niceread.ui.view.GankItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:28
 */
public class GankItemFragment extends BaseMvpFragment<GankItemView, GankItemPresenter> implements GankItemView,
        BaseQuickAdapter.OnRecyclerViewItemClickListener, BaseQuickAdapter.RequestLoadMoreListener{

    private String mSubtype;
    private int mPageCount = 0;
    private int mLastVisibleItemPosition;

    private GankItemAdapter mGankItemAdapter;

    @BindView(R.id.type_item_recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.type_item_fab)
    FloatingActionButton mFab;

    @OnClick(R.id.type_item_fab)
    void onClick() {
        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    protected GankItemPresenter initPresenter() {
        return new GankItemPresenter();
    }

    @Override
    protected void fetchData() {
        mPresenter.getGankItemData("data/" + mSubtype + "/10/" + (++mPageCount));
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_type_item_layout;
    }

    @Override
    protected void initView() {
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        //实现首次自动显示加载提示
//        mSwipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                mSwipeRefreshLayout.setRefreshing(true);
//            }
//        });

        mGankItemAdapter = new GankItemAdapter(R.layout.item_gank_layout, new ArrayList<GankItemData>());
        mGankItemAdapter.setOnRecyclerViewItemClickListener(this);
        mGankItemAdapter.openLoadMore(10, true);
        mGankItemAdapter.setOnLoadMoreListener(this);

        View view = LayoutInflater.from(mActivity).inflate(R.layout.load_start_layout, (ViewGroup) mRecyclerView.getParent(), false);
        mGankItemAdapter.setEmptyView(view);
        mRecyclerView.setAdapter(mGankItemAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        //RecyclerView滚动位置
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (mLastVisibleItemPosition < manager.findLastVisibleItemPosition() && mLastVisibleItemPosition == 12) {
                    mFab.show();
                }

                if (mLastVisibleItemPosition > manager.findLastVisibleItemPosition() && mFab.isShown()) {
                    mFab.hide();
                }

                mLastVisibleItemPosition = manager.findLastVisibleItemPosition();
            }
        });
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
        if (mPageCount > 1) {
            mGankItemAdapter.notifyDataChangedAfterLoadMore(data, true);
        } else {
            mGankItemAdapter.setNewData(data);
        }
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

    @Override
    public void onItemClick(View view, int i) {
        GankItemData data = mGankItemAdapter.getItem(i);
        Intent intent = new Intent(mActivity, GankDetailActivity.class);
        intent.putExtra("gank_item_data", data);
        startActivity(intent);
    }

    @Override
    public void onLoadMoreRequested() {
        fetchData();
    }
}
