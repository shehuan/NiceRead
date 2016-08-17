package com.otherhshe.niceread.model;

import rx.Observable;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:30
 */
public interface IGirlItemModel {
    Observable<String> getGirlItemData(String cid, int page);
}
