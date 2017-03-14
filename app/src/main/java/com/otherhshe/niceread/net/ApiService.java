package com.otherhshe.niceread.net;

import com.otherhshe.niceread.api.GankService;
import com.otherhshe.niceread.api.GirlService;
import com.otherhshe.niceread.api.SplashService;

public class ApiService {
    public static ApiService getInstance() {
        return ApiService.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ApiService INSTANCE = new ApiService();
    }

    public <S> S initService(Class<S> service) {
        if (service.equals(GankService.class) || service.equals(SplashService.class)) {
            return NetManager.getInstance().create(service);
        } else if (service.equals(GirlService.class)) {
            return NetManager.getInstance().create1(service);
        }
        return null;
    }
}
