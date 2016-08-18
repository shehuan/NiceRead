package com.otherhshe.niceread.ui.adapter.baseadapter;

import android.view.View;

/**
 * Author: Othershe
 * Time: 2016/8/18 15:42
 */
public interface OnItemClickListener<T> {
    void onCommonItemClick(View view, T t, int position);
    void onLoadItemClick();
}

