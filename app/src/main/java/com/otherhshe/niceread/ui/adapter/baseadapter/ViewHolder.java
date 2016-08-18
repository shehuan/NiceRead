package com.otherhshe.niceread.ui.adapter.baseadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Author: Othershe
 * Time: 2016/8/18 11:48
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    /**
     * 自扩展构造方法
     *
     * @param context
     * @param itemView
     * @param parent
     */
    public ViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static ViewHolder get(Context context, int layoutId, ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ViewHolder(context, itemView, parent);
    }

    /**
     * 通过id获得控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 辅助方法
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public ViewHolder setBgRes(int viewId, int resId) {
        View view = getView(viewId);
        view.setBackgroundResource(resId);
        return this;
    }
}
