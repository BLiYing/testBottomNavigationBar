package com.example.wangchang.testbottomnavigationbar.http;


import com.example.wangchang.testbottomnavigationbar.model.ZhuangbiImage;
import com.example.wangchang.testbottomnavigationbar.enity.HttpResult;
import com.example.wangchang.testbottomnavigationbar.enity.Subject;
import com.example.wangchang.testbottomnavigationbar.enity.SubjectEntity;
import com.example.wangchang.testbottomnavigationbar.enity.UserEntity;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by helin on 2016/10/9 17:09.
 */

public interface ApiGetDoubanMovie {
    @GET("/student/mobileRegister")
    Observable<HttpResult<UserEntity>> login(@Query("phone") String phone, @Query("password") String psw);


    @GET("top250")
    Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);

    @GET("top250")
    Observable<HttpResult<SubjectEntity>> getUser( @Query("touken") String touken);

    @GET("search")
    Observable<List<ZhuangbiImage>> search(@Query("q") String query);



}
