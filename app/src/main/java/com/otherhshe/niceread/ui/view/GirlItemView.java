package com.otherhshe.niceread.ui.view;

import com.otherhshe.niceread.model.GirlItemData;

import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/16 12:10
 */
public interface GirlItemView extends IBaseView{
    void onSuccess(List<GirlItemData> data);
}
