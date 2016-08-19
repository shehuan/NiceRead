package com.otherhshe.niceread.ui.fragemnt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.otherhshe.niceread.R;
import com.otherhshe.niceread.data.GirlItemData;
import com.otherhshe.niceread.presenter.GirlItemPresenter;
import com.otherhshe.niceread.ui.activity.GirlDetailActivity;
import com.otherhshe.niceread.ui.adapter.GirlItemAdapter;
import com.otherhshe.niceread.ui.adapter.MyAdapter;
import com.otherhshe.niceread.ui.adapter.baseadapter.RefreshAdapter;
import com.otherhshe.niceread.ui.view.GirlItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author: Othershe
 * Time: 2016/8/17 10:57
 */
public class GirlItemFragment extends BaseMvpFragment<GirlItemView, GirlItemPresenter> implements GirlItemView{
    private String mSubtype;
    private int mPageCount = 1;

    private GirlItemAdapter mGirlItemAdapter;
    MyAdapter adapter;

    @BindView(R.id.type_item_recyclerview)
    RecyclerView mRecyclerView;

    @Override
    protected GirlItemPresenter initPresenter() {
        return new GirlItemPresenter();
    }

    @Override
    protected void fetchData() {
        mPresenter.getGirlItemData(mSubtype, mPageCount);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_type_item_layout;
    }

    @Override
    protected void initView() {
//        mGirlItemAdapter = new GirlItemAdapter(R.layout.item_girl_layout, new ArrayList<GirlItemData>());
//        mGirlItemAdapter.setOnRecyclerViewItemClickListener(this);
//        mGirlItemAdapter.openLoadMore(10, true);
//        mGirlItemAdapter.setOnLoadMoreListener(this);

//        View view = LayoutInflater.from(mActivity).inflate(R.layout.load_start_layout, (ViewGroup) mRecyclerView.getParent(), false);
//        mGirlItemAdapter.setEmptyView(view);

         adapter = new MyAdapter(mActivity, new ArrayList<GirlItemData>());
        mRecyclerView.setAdapter(adapter);

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//据说可防止Item切换
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int[] lastVisiblePositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
                if (findMax(lastVisiblePositions) + 1 == adapter.getItemCount()){
                    adapter.updateRefreshState(RefreshAdapter.STATE_START);
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
//        if (mPageCount > 1) {
//            mGirlItemAdapter.notifyDataChangedAfterLoadMore(data, true);
//        } else {
//            mGirlItemAdapter.setNewData(data);
//        }

        adapter.notifyTopRefresh(data);
        mPageCount++;
    }

    @Override
    public void onError() {

    }

    public static GirlItemFragment newInstance(String subtype) {
        GirlItemFragment fragment = new GirlItemFragment();
        Bundle arguments = new Bundle();
        arguments.putString(SUB_TYPE, subtype);
        fragment.setArguments(arguments);
        return fragment;
    }

//    @Override
//    public void onItemClick(View view, int i) {
//        GirlItemData data = mGirlItemAdapter.getItem(i);
//        Intent intent = new Intent(mActivity, GirlDetailActivity.class);
//        intent.putExtra("girl_item_data", data);
//        startActivity(intent);
//    }
//
//    @Override
//    public void onLoadMoreRequested() {
//        fetchData();
//    }
}
