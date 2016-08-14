package com.otherhshe.niceread.model.impl;

import com.otherhshe.niceread.api.GankItemService;
import com.otherhshe.niceread.data.GankItemData;
import com.otherhshe.niceread.data.HttpResult;
import com.otherhshe.niceread.model.IGankItemModel;
import com.otherhshe.niceread.net.NetManager;

import java.util.List;

import rx.Observable;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:30
 */
public class GankItemModelImpl implements IGankItemModel {

    @Override
    public Observable<HttpResult<List<GankItemData>>> getGankItemData(String suburl) {
        GankItemService service = NetManager.getInstance().create(GankItemService.class);
        return service.getGankItemData(suburl);
    }
}
