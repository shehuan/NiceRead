package com.otherhshe.niceread.ui.fragemnt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.otherhshe.niceread.R;
import com.otherhshe.niceread.data.GirlItemData;
import com.otherhshe.niceread.presenter.GirlItemPresenter;
import com.otherhshe.niceread.sevice.DataService;
import com.otherhshe.niceread.ui.activity.GirlDetailActivity;
import com.otherhshe.niceread.ui.adapter.GirlItemAdapterFooter;
import com.otherhshe.niceread.ui.adapter.baseadapter.OnItemClickListener;
import com.otherhshe.niceread.ui.adapter.baseadapter.FooterRefreshAdapter;
import com.otherhshe.niceread.ui.adapter.baseadapter.ViewHolder;
import com.otherhshe.niceread.ui.view.GirlItemView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author: Othershe
 * Time: 2016/8/17 10:57
 */
public class GirlItemFragment extends BaseMvpFragment<GirlItemView, GirlItemPresenter> implements GirlItemView, SwipeRefreshLayout.OnRefreshListener {
    private int PAGE_COUNT = 1;
    private String mSubtype;
    private int mTempPageCount = 2;

    private GirlItemAdapterFooter mGirlItemAdapter;

    private boolean isLoadMore;//是否是底部加载更多

    @BindView(R.id.type_item_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.type_item_swipfreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected GirlItemPresenter initPresenter() {
        return new GirlItemPresenter();
    }

    @Override
    protected void fetchData() {
        mPresenter.getGirlItemData(mSubtype, PAGE_COUNT);
        Log.e("GirlItemFragment", mSubtype + "-------" + PAGE_COUNT);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_type_item_layout;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //实现首次自动显示加载提示
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        mGirlItemAdapter = new GirlItemAdapterFooter(mActivity, new ArrayList<GirlItemData>());
        mGirlItemAdapter.setOnItemClickListener(new OnItemClickListener<GirlItemData>() {
            @Override
            public void onCommonItemClick(ViewHolder viewHolder, GirlItemData girlItemData, int position) {
                Intent intent = new Intent(mActivity, GirlDetailActivity.class);
                intent.putExtra("girl_item_data", girlItemData);
                startActivity(intent);
            }

            @Override
            public void onLoadItemClick() {
                mGirlItemAdapter.updateRefreshState(FooterRefreshAdapter.STATE_START);
                fetchData();
            }
        });

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//可防止Item切换
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mGirlItemAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int[] lastVisiblePositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
                //防止重复请求
                if (PAGE_COUNT == mTempPageCount) {
                    return;
                }

                if (findMax(lastVisiblePositions) + 1 == mGirlItemAdapter.getItemCount() && mGirlItemAdapter.getItemCount() > 1) {
                    //已到达底部，开始加载更多
                    isLoadMore = true;
                    mGirlItemAdapter.updateRefreshState(FooterRefreshAdapter.STATE_START);
                    PAGE_COUNT = mTempPageCount;
                    fetchData();
                }
            }
        });
    }

    private int findMax(int[] lastVisiblePositions) {
        int max = lastVisiblePositions[0];
        for (int value : lastVisiblePositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    protected void initData() {
        if (getArguments() == null) {
            return;
        }
        mSubtype = getArguments().getString(SUB_TYPE);
    }

    @Override
    public void onSuccess(List<GirlItemData> data) {
        DataService.startService(mActivity, data, mSubtype);
    }

    @Override
    public void onError() {
        if (isLoadMore) {
            mGirlItemAdapter.updateRefreshState(FooterRefreshAdapter.STATE_ERROR);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    public static GirlItemFragment newInstance(String subtype) {
        GirlItemFragment fragment = new GirlItemFragment();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dataEvent(List<GirlItemData> data) {
        if (!data.get(0).getSubtype().equals(mSubtype)) {
            return;
        }

        if (isLoadMore) {
            if (data.size() == 0) {
                mGirlItemAdapter.updateRefreshState(FooterRefreshAdapter.STATE_FINISH);
            } else {
                mTempPageCount++;
                mGirlItemAdapter.notifyBottomRefresh(data);
            }
        } else {
            mGirlItemAdapter.notifyTopRefresh(data);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
