package com.otherhshe.niceread.api;

import com.otherhshe.niceread.constant.Api;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Author: Othershe
 * Time: 2016/8/17 11:49
 */
public interface GirlDetailService {
    String BASE_URL = Api.URL_GET_GIRL;

    @GET("{id}")
    Observable<String> getGirlDetailData(@Path("id") String id);
}
