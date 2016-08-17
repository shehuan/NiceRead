package com.otherhshe.niceread.model.impl;

import com.otherhshe.niceread.api.GirlItemService;
import com.otherhshe.niceread.model.IGirlItemModel;
import com.otherhshe.niceread.net.NetManager;

import rx.Observable;

/**
 * Author: Othershe
 * Time: 2016/8/17 14:42
 */
public class GirlItemModelImpl implements IGirlItemModel {
    @Override
    public Observable<String> getGirlItemData(String cid, int page) {
        GirlItemService service = NetManager.getInstance().create1(GirlItemService.class);
        return service.getGirlItemData(cid, page);
    }
}
