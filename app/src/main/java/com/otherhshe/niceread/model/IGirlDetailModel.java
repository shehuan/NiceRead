package com.otherhshe.niceread.model;

import rx.Observable;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:30
 */
public interface IGirlDetailModel {
    Observable<String> getGirlDetailData(String id);
}
