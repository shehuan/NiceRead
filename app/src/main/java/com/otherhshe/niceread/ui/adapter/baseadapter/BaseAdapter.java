package com.otherhshe.niceread.ui.adapter.baseadapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/29 09:46
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_COMMON_VIEW = 100001;
    public static final int TYPE_FOOTER_VIEW = 100002;
    public static final int TYPE_EMPTY_VIEW = 100003;
    public static final int TYPE_DEFAULT_VIEW = 100004;

    private OnLoadMoreListener mLoadMoreListener;
    private OnItemClickListeners<T> mItemClickListener;

    protected Context mContext;
    protected List<T> mDatas;
    private boolean mOpenLoadMore;
    private boolean isAutoLoadMore = true;

    private View mLoadingView;
    private View mLoadFailedView;
    private View mLoadEndView;
    private View mEmptyView;
    private RelativeLayout mFooterLayout;

    protected abstract void convert(ViewHolder holder, T data);

    protected abstract int getItemLayoutId();

    public BaseAdapter(Context context, List<T> datas, boolean isOpenLoadMore) {
        mContext = context;
        mDatas = datas == null ? new ArrayList<T>() : datas;
        mOpenLoadMore = isOpenLoadMore;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_COMMON_VIEW:
                viewHolder = ViewHolder.create(mContext, getItemLayoutId(), parent);
                break;
            case TYPE_FOOTER_VIEW:
                if (mFooterLayout == null) {
                    mFooterLayout = new RelativeLayout(mContext);
                }
                viewHolder = ViewHolder.create(mFooterLayout);
                break;
            case TYPE_EMPTY_VIEW:
                viewHolder = ViewHolder.create(mEmptyView);
                break;
            case TYPE_DEFAULT_VIEW:
                viewHolder = ViewHolder.create(new View(mContext));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_COMMON_VIEW:
                bindCommonItem(holder, position);
                break;
        }
    }

    private void bindCommonItem(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        convert(viewHolder, mDatas.get(position));

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(viewHolder, mDatas.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + getFooterViewCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.isEmpty() && mEmptyView != null) {
            return TYPE_EMPTY_VIEW;
        }

        if (isFooterView(position)) {
            return TYPE_FOOTER_VIEW;
        }

        if (mDatas.isEmpty()) {
            return TYPE_DEFAULT_VIEW;
        }

        return TYPE_COMMON_VIEW;
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }

    /**
     * 是否是FooterView
     *
     * @param position
     * @return
     */
    private boolean isFooterView(int position) {
        return mOpenLoadMore && getItemCount() > 1 && position >= getItemCount() - 1;
    }

    /**
     * StaggeredGridLayoutManager模式时，FooterView可占据一行
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isFooterView(holder.getLayoutPosition())) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    /**
     * GridLayoutManager模式时， FooterView可占据一行，判断RecyclerView是否到达底部
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) layoutManager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isFooterView(position)) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }

        startLoadMore(recyclerView, layoutManager);
    }


    /**
     * 判断列表是否滑动到底部
     *
     * @param recyclerView
     * @param layoutManager
     */
    private void startLoadMore(RecyclerView recyclerView, final RecyclerView.LayoutManager layoutManager) {
        if (!mOpenLoadMore || mLoadMoreListener == null) {
            return;
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!isAutoLoadMore && findLastVisibleItemPosition(layoutManager) + 1 == getItemCount()) {
                        scrollLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isAutoLoadMore && findLastVisibleItemPosition(layoutManager) + 1 == getItemCount()) {
                    scrollLoadMore();
                } else if (isAutoLoadMore) {
                    isAutoLoadMore = false;
                }
            }
        });
    }

    /**
     * 到达底部开始刷新
     */
    private void scrollLoadMore() {
        if (mFooterLayout.getChildAt(0) == mLoadingView) {
            mLoadMoreListener.onLoadMore(false);
        }
    }

    private int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            return findMax(lastVisibleItemPositions);
        }
        return -1;
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

    private void removeFooterView() {
        mFooterLayout.removeAllViews();
    }

    private void addFooterView(View footerView) {
        if (footerView == null) {
            return;
        }

        if (mFooterLayout == null) {
            mFooterLayout = new RelativeLayout(mContext);
        }
        removeFooterView();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mFooterLayout.addView(footerView, params);
    }

    public void setLoadMoreData(List<T> datas) {
        int size = mDatas.size();
        mDatas.addAll(datas);
        notifyItemInserted(size);
    }

    public void setData(List<T> datas) {
        mDatas.addAll(0, datas);
        notifyDataSetChanged();
    }

    public void setNewData(List<T> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 初始化加载中布局
     *
     * @param loadingView
     */
    public void setLoadingView(View loadingView) {
        mLoadingView = loadingView;
        addFooterView(mLoadingView);
    }

    public void setLoadingView(int loadingId) {
        setLoadingView(inflate(loadingId));
    }

    /**
     * 初始加载失败布局
     *
     * @param loadFailedView
     */
    public void setLoadFailedView(View loadFailedView) {
        if (loadFailedView == null) {
            return;
        }
        mLoadFailedView = loadFailedView;
        addFooterView(mLoadFailedView);
        mLoadFailedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFooterView(mLoadingView);
                mLoadMoreListener.onLoadMore(true);
            }
        });
    }

    public void setLoadFailedView(int loadFailedId) {
        setLoadFailedView(inflate(loadFailedId));
    }

    /**
     * 初始化全部加载完成布局
     *
     * @param loadEndView
     */
    public void setLoadEndView(View loadEndView) {
        mLoadEndView = loadEndView;
        addFooterView(mLoadEndView);
    }

    public void setLoadEndView(int loadEndId) {
        setLoadEndView(inflate(loadEndId));
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    public int getFooterViewCount() {
        return mOpenLoadMore ? 1 : 0;
    }


    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
    }

    public void setOnItemClickListener(OnItemClickListeners<T> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    private View inflate(int layoutId) {
        if (layoutId <= 0) {
            return null;
        }
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }
}
