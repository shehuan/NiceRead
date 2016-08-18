package com.otherhshe.niceread.ui.fragemnt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.otherhshe.niceread.R;
import com.otherhshe.niceread.data.GankItemData;
import com.otherhshe.niceread.presenter.GankItemPresenter;
import com.otherhshe.niceread.ui.activity.GankDetailActivity;
import com.otherhshe.niceread.ui.adapter.MyAdapter;
import com.otherhshe.niceread.ui.adapter.baseadapter.OnItemClickListener;
import com.otherhshe.niceread.ui.adapter.baseadapter.RefreshAdapter;
import com.otherhshe.niceread.ui.view.GankItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:28
 */
public class GankItemFragment extends BaseMvpFragment<GankItemView, GankItemPresenter> implements GankItemView, SwipeRefreshLayout.OnRefreshListener {

    private int PAGE_COUNT = 1;

    private String mSubtype;
    private int mTempPageCount = 2;
    private int mLastVisibleItemPosition;

    private MyAdapter adapter;

    private boolean isLoadMore;

    @BindView(R.id.type_item_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.type_item_fab)
    FloatingActionButton mFab;

    @BindView(R.id.type_item_swipfreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

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
        mPresenter.getGankItemData("data/" + mSubtype + "/10/" + PAGE_COUNT);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_type_item_layout;
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //实现首次自动显示加载提示
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        adapter = new MyAdapter(mActivity, new ArrayList<GankItemData>());
        adapter.setOnItemClickListener(new OnItemClickListener<GankItemData>() {
            @Override
            public void onCommonItemClick(View view, GankItemData gankItemData, int position) {
                Intent intent = new Intent(mActivity, GankDetailActivity.class);
                intent.putExtra("gank_item_data", gankItemData);
                startActivity(intent);
            }

            @Override
            public void onLoadItemClick() {
                adapter.updateRefreshState(RefreshAdapter.STATE_START);
                fetchData();
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(adapter);

        //RecyclerView滚动监听
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mLastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    if (mLastVisibleItemPosition > 0 && mLastVisibleItemPosition + 1 == adapter.getItemCount()) {
                        //已到达底部，开始加载更多
                        isLoadMore = true;
                        adapter.updateRefreshState(RefreshAdapter.STATE_START);
                        PAGE_COUNT = mTempPageCount;
                        fetchData();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (mLastVisibleItemPosition < layoutManager.findLastVisibleItemPosition() && mLastVisibleItemPosition == 12) {
                    mFab.show();
                }

                if (mLastVisibleItemPosition > layoutManager.findLastVisibleItemPosition() && mFab.isShown()) {
                    mFab.hide();
                }

                mLastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
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
        if (isLoadMore) {
            if (data.size() == 0) {
                adapter.updateRefreshState(RefreshAdapter.STATE_FINISH);
            }
            adapter.notifyBottomRefresh(data);
            mTempPageCount++;
        } else {
            adapter.notifyTopRefresh(data);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onError() {
        if (isLoadMore) {
            adapter.updateRefreshState(RefreshAdapter.STATE_ERROR);
        }
    }

    public static GankItemFragment newInstance(String subtype) {
        GankItemFragment fragment = new GankItemFragment();
        Bundle arguments = new Bundle();
        arguments.putString(SUB_TYPE, subtype);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        PAGE_COUNT = 1;
        fetchData();
    }
}
