package com.otherhshe.niceread.ui.adapter.baseadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/18 11:48
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        mContext = context;
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, mLayoutId, parent);
        setListener(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    protected abstract void convert(ViewHolder holder, T t);

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
}
