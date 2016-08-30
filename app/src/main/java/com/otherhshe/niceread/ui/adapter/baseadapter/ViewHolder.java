package com.otherhshe.niceread.ui.adapter.baseadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.otherhshe.niceread.utils.ResourceUtil;

/**
 * Author: Othershe
 * Time: 2016/8/18 11:48
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;

    /**
     * 构造方法
     *
     * @param itemView
     */
    public ViewHolder(View itemView) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static ViewHolder create(Context context, int layoutId, ViewGroup parent) {
        View itemView = ResourceUtil.inflate(context, layoutId, parent);
        return new ViewHolder(itemView);
    }

    public static ViewHolder create(View itemView) {
        return new ViewHolder(itemView);
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
