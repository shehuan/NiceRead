package com.otherhshe.niceread.ui.fragemnt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.otherhshe.niceread.R;
import com.otherhshe.niceread.model.GankItemData;
import com.otherhshe.niceread.presenter.GankItemPresenter;
import com.otherhshe.niceread.ui.activity.GankDetailActivity;
import com.otherhshe.niceread.ui.adapter.GankItemAdapter;
import com.otherhshe.niceread.ui.adapter.baseadapter.OnItemClickListeners;
import com.otherhshe.niceread.ui.adapter.baseadapter.OnLoadMoreListener;
import com.otherhshe.niceread.ui.adapter.baseadapter.ViewHolder;
import com.otherhshe.niceread.ui.view.GankItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:28
 */
public class GankItemFragment extends BaseMvpFragment implements GankItemView, SwipeRefreshLayout.OnRefreshListener {

    private int PAGE_COUNT = 1;

    private String mSubtype;
    private int mTempPageCount = 2;
    private int mLastVisibleItemPosition;

    private GankItemAdapter mGankItemAdapter;

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
    protected void fetchData() {
        GankItemPresenter presenter = new GankItemPresenter(this);
        presenter.getGankItemData("data/" + mSubtype + "/10/" + PAGE_COUNT);
        addPresenter(presenter);
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

        mGankItemAdapter = new GankItemAdapter(mActivity, new ArrayList<GankItemData>(), true);
        mGankItemAdapter.setLoadingView(R.layout.load_loading_layout);
        mGankItemAdapter.setOnItemClickListener(new OnItemClickListeners<GankItemData>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, GankItemData gankItemData, int position) {
                Intent intent = new Intent(mActivity, GankDetailActivity.class);
                intent.putExtra("gank_item_data", gankItemData);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity,
                        viewHolder.getView(R.id.gank_item_icon), "shareView");
                ActivityCompat.startActivity(mActivity, intent, optionsCompat.toBundle());
            }
        });

        mGankItemAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (PAGE_COUNT == mTempPageCount && !isReload) {
                    return;
                }
                isLoadMore = true;
                PAGE_COUNT = mTempPageCount;
                fetchData();
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mGankItemAdapter);

        //RecyclerView滚动监听
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
                mGankItemAdapter.setLoadEndView(R.layout.load_end_layout);
            } else {
                mGankItemAdapter.setLoadMoreData(data);
                mTempPageCount++;
            }
        } else {
            mGankItemAdapter.setNewData(data);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onError() {
        if (isLoadMore) {
            mGankItemAdapter.setLoadFailedView(R.layout.load_failed_layout);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
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
        mTempPageCount = 2;
        fetchData();
    }
}
