package com.otherhshe.niceread.api;

import com.otherhshe.niceread.data.SplashData;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Author: Othershe
 * Time:  2016/8/11 12:30
 */
public interface SplashService {
    String BASE_URL = "http://news-at.zhihu.com/api/3/start-image/";

    @GET("1080*1920")
    Observable<SplashData> getSplashPic();
}
