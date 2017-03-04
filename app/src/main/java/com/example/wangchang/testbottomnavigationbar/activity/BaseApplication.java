package com.example.wangchang.testbottomnavigationbar.activity;

import android.app.Application;

import com.example.wangchang.testbottomnavigationbar.exception.LocalFileHandler;
import com.helin.rxsample.http.Api;
import com.helin.rxsample.util.LogUtil;
import com.orhanobut.hawk.Hawk;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by helin on 2016/11/11 11:15.
 */

public class BaseApplication extends Application implements Thread.UncaughtExceptionHandler {
    private static BaseApplication mApplication;
    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;
        Hawk.init(this).build();
        //配置是否显示log
        LogUtil.isDebug = true;

        //配置程序异常退出处理
//        Thread.setDefaultUncaughtExceptionHandler(new LocalFileHandler(this));

    }



    public static OkHttpClient defaultOkHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Api.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Api.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Api.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        return client;
    }

    public static BaseApplication getInstance(){ return mApplication;}

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }
}
