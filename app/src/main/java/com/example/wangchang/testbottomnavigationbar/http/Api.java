package com.example.wangchang.testbottomnavigationbar.http;


import com.parkingwang.okhttp3.LogInterceptor.LogInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by helin on 2016/11/10 10:28.
 */

public class Api {
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static ApiGetDoubanMovie SERVICE;
    private static ApiGankGirls sApiGankGirls;
    /**
     * 请求超时时间
     */
    public static final int DEFAULT_TIMEOUT = 10000;

    public static ApiGetDoubanMovie getDoubanService() {


        SERVICE = new Retrofit.Builder()
                .client(defaultOkHttpClient())
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build().create(ApiGetDoubanMovie.class);

        return SERVICE;
    }

    public static ApiGankGirls getGankService() {
        if (sApiGankGirls == null) {
            sApiGankGirls = new Retrofit.Builder()
                    .client(defaultOkHttpClient())
                    .baseUrl(Url.BASE_URL_GANK)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build().create(ApiGankGirls.class);
        }
        return sApiGankGirls;

    }

    private static OkHttpClient defaultOkHttpClient() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Api.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Api.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Api.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new LogInterceptor())
                .build();
        return client;
    }

}
