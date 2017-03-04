package com.helin.rxsample.http;


import com.example.wangchang.testbottomnavigationbar.activity.BaseApplication;

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

            //手动创建一个OkHttpClient并设置超时时间
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//            httpClientBuilder.addNetworkInterceptor(new MockInterceptor());
            /**
             *  拦截器
             */
           /* httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    HttpUrl.Builder authorizedUrlBuilder = request.url()
                            .newBuilder()
                            //添加统一参数 如手机唯一标识符,token等
                            .addQueryParameter("key1","value1")
                            .addQueryParameter("key2", "value2");

                    Request newRequest = request.newBuilder()
                            //对所有请求添加请求头
                            .header("mobileFlag", "adfsaeefe").addHeader("type", "4")
                            .method(request.method(), request.body())
                            .url(authorizedUrlBuilder.build())
                            .build();
                    return  chain.proceed(newRequest);
                }
            });*/

            /*SERVICE = new Retrofit.Builder()
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(Url.BASE_URL)
                    .build().create(ApiGetDoubanMovie.class);*/
            SERVICE = new Retrofit.Builder()
                    .client(BaseApplication.defaultOkHttpClient())
                    .baseUrl(Url.BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build().create(ApiGetDoubanMovie.class);

        return SERVICE;
    }

    public static ApiGankGirls getGankService() {
        if (sApiGankGirls == null) {
            //手动创建一个OkHttpClient并设置超时时间
           /* OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);*/
//            OkHttpClient okHttpClient = new OkHttpClient();
            sApiGankGirls = new Retrofit.Builder()
                    .client(BaseApplication.defaultOkHttpClient())
                    .baseUrl("http://gank.io/api/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build().create(ApiGankGirls.class);
        }
        return sApiGankGirls;

    }

}
