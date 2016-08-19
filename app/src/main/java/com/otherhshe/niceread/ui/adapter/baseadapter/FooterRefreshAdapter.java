package com.otherhshe.niceread.ui.adapter.baseadapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.otherhshe.niceread.R;

import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/18 15:42
 */
public abstract class FooterRefreshAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    public static final int STATE_START = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_FINISH = 3;

    private int mFooterLoadState;

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;

    private ViewHolder mFooterViewHolder;

    private OnItemClickListener<T> mOnItemClickListener;

    protected abstract void convert(RecyclerView.ViewHolder holder, T t);

    protected abstract int getItemLayoutId();

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public FooterRefreshAdapter(Context context, List<T> datas) {
        mContext = context;
        mLayoutId = getItemLayoutId();
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            ViewHolder viewHolder = ViewHolder.create(mContext, mLayoutId, parent);
            setCommonListener(viewHolder);
            return viewHolder;
        } else {
            mFooterViewHolder = ViewHolder.create(mContext, R.layout.footer_loading, parent);
            setFooterListener(mFooterViewHolder);
            return mFooterViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isFooterView(position)) {
            return;
        }
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mDatas.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
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
        return position >= getItemCount() - 1;
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
     * GridLayoutManager模式时， FooterView可占据一行
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
    }

    protected void setCommonListener(final ViewHolder viewHolder) {

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    mOnItemClickListener.onCommonItemClick(v, mDatas.get(position), position);
                }
            }
        });
    }

    protected void setFooterListener(final ViewHolder viewHolder) {
        viewHolder.getView(R.id.id_footer_load_error_end).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFooterLoadState == STATE_ERROR) {
                    viewHolder.getView(R.id.id_footer_loading).setVisibility(View.VISIBLE);
                    viewHolder.getView(R.id.id_footer_load_error_end).setVisibility(View.INVISIBLE);
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onLoadItemClick();
                    }
                }
            }
        });
    }

    /**
     * 更新底部加载提示的状态
     *
     * @param state
     */
    public void updateRefreshState(int state) {
        mFooterLoadState = state;
        switch (state) {
            case STATE_START:
                mFooterViewHolder.getView(R.id.id_footer_loading).setVisibility(View.VISIBLE);
                mFooterViewHolder.getView(R.id.id_footer_load_error_end).setVisibility(View.INVISIBLE);
                break;
            case STATE_ERROR:
                mFooterViewHolder.getView(R.id.id_footer_loading).setVisibility(View.INVISIBLE);
                mFooterViewHolder.getView(R.id.id_footer_load_error_end).setVisibility(View.VISIBLE);
                break;
            case STATE_FINISH:
                ((TextView) mFooterViewHolder.getView(R.id.id_footer_load_error_end)).setText(R.string.load_end_tip);
                mFooterViewHolder.getView(R.id.id_footer_loading).setVisibility(View.INVISIBLE);
                break;
        }
    }

    /**
     * 更新顶部加载最新
     *
     * @param datas
     */
    public void notifyBottomRefresh(List<T> datas) {
        int size = mDatas.size();
        mDatas.addAll(datas);
        notifyItemInserted(size);
    }

    /**
     * 更新底部加载更多
     *
     * @param datas
     */
    public void notifyTopRefresh(List<T> datas) {
        mDatas.clear();
        mDatas = datas;
        notifyDataSetChanged();
    }
}
