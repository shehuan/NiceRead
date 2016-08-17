package com.otherhshe.niceread.model.impl;

import com.otherhshe.niceread.api.GirlDetailService;
import com.otherhshe.niceread.api.GirlItemService;
import com.otherhshe.niceread.model.IGirlDetailModel;
import com.otherhshe.niceread.model.IGirlItemModel;
import com.otherhshe.niceread.net.NetManager;

import rx.Observable;

/**
 * Author: Othershe
 * Time: 2016/8/17 14:42
 */
public class GirlDetailModelImpl implements IGirlDetailModel {

    @Override
    public Observable<String> getGirlDetailData(String id) {
        GirlDetailService service = NetManager.getInstance().create1(GirlDetailService.class);
        return service.getGirlDetailData(id);
    }
}
