// (c)2016 Flipboard Inc, All Rights Reserved.

package com.example.wangchang.testbottomnavigationbar.http;

import com.example.wangchang.testbottomnavigationbar.enity.HttpResultGank;
import com.example.wangchang.testbottomnavigationbar.enity.ResultsEntity;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ApiGankGirls {
    @GET("data/福利/{number}/{page}")
    Observable<HttpResultGank<List<ResultsEntity>>> getBeauties(@Path("number") int number, @Path("page") int page);
}
