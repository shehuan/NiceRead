package com.otherhshe.niceread.model;

import com.otherhshe.niceread.data.GankItemData;
import com.otherhshe.niceread.data.HttpResult;

import java.util.List;

import rx.Observable;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:30
 */
public interface IGankItemModel {
    Observable<HttpResult<List<GankItemData>>> getGankItemData(String suburl);
}
