package com.otherhshe.niceread.ui.view;

import com.otherhshe.niceread.data.GankItemData;

import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:31
 */
public interface GankItemView extends IBaseView {
    void onSuccess(List<GankItemData> data);
}
