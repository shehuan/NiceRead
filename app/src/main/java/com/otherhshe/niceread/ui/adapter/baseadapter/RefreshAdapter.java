package com.otherhshe.niceread.ui.adapter.baseadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.otherhshe.niceread.R;
import com.otherhshe.niceread.utils.ResourceUtil;

import java.util.List;

public abstract class RefreshAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    public static final int STATE_START = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_FINISH = 3;

    private int mFooterLoadState;

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;

    private FooterViewHolder mFooterViewHolder;

    private OnItemClickListener<T> mOnItemClickListener;

    protected abstract void convert(RecyclerView.ViewHolder holder, T t);

    protected abstract int getItemLayoutId();

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public RefreshAdapter(Context context, List<T> datas) {
        mContext = context;
        mLayoutId = getItemLayoutId();
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            ViewHolder viewHolder = ViewHolder.get(mContext, mLayoutId, parent);
            setListener(viewHolder);
            return viewHolder;
        } else {
            if (mFooterViewHolder == null) {
                mFooterViewHolder = new FooterViewHolder(ResourceUtil.inflate(mContext, R.layout.footer_loading, parent));
                mFooterViewHolder.mLoadMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mFooterLoadState == STATE_ERROR) {
                            mFooterViewHolder.mLoading.setVisibility(View.VISIBLE);
                            mFooterViewHolder.mLoadMore.setVisibility(View.INVISIBLE);
                            if (mOnItemClickListener != null) {
                                mOnItemClickListener.onLoadItemClick();
                            }
                        }
                    }
                });
            }
            return mFooterViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            convert(holder, mDatas.get(position));
        }
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

    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    protected void setListener(final ViewHolder viewHolder) {

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

    public void updateRefreshState(int state) {
        mFooterLoadState = state;
        if (mFooterLoadState == STATE_START) {
            mFooterViewHolder.mLoading.setVisibility(View.VISIBLE);
            mFooterViewHolder.mLoadMore.setVisibility(View.INVISIBLE);
        }

        if (mFooterLoadState == STATE_ERROR) {
            mFooterViewHolder.mLoading.setVisibility(View.INVISIBLE);
            mFooterViewHolder.mLoadMore.setVisibility(View.VISIBLE);
        }

        if (mFooterLoadState == STATE_FINISH) {
            mFooterViewHolder.mLoadMore.setText("这是我的底线");
        }
    }

    public void notifyBottomRefresh(List<T> datas) {
        int size = mDatas.size();
        mDatas.addAll(datas);
        notifyItemInserted(size);
    }

    public void notifyTopRefresh(List<T> datas) {
        mDatas.clear();
        mDatas = datas;
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mLoadMore;
        public final LinearLayout mLoading;

        public FooterViewHolder(View itemView) {
            super(itemView);
            mLoadMore = (TextView) itemView.findViewById(R.id.id_footer_load_more);
            mLoading = (LinearLayout) itemView.findViewById(R.id.id_footer_loading);
        }
    }
}
