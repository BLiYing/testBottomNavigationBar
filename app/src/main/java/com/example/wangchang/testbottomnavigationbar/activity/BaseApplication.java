package com.example.wangchang.testbottomnavigationbar.activity;

import android.app.Application;

import com.example.wangchang.testbottomnavigationbar.util.LogUtil;
import com.orhanobut.hawk.Hawk;

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

    public static BaseApplication getInstance(){ return mApplication;}

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }
}
