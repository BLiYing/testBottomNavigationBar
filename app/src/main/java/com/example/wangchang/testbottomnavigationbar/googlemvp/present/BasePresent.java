package com.example.wangchang.testbottomnavigationbar.googlemvp.present;

/**
 * author : liying
 * e-mail : liying@yuntongxun.com
 * time   : 2023/05/23 10:20
 * desc   :
 * version: 2.5.0.2
 */
public interface BasePresent {
    //用于开始获取数据并调用View的方法更新UI，一般在Fragment的onResume方法中调用。
    void start();
}
