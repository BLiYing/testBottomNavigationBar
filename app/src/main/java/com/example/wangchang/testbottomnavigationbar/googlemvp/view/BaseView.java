package com.example.wangchang.testbottomnavigationbar.googlemvp.view;

/**
 * author : liying
 * e-mail : liying@yuntongxun.com
 * time   : 2023/05/23 10:23
 * desc   :
 * version: 2.5.0.2
 */
public interface BaseView<T> {
    //用于绑定Presenter，在BasePresenter实现类的构造器中，传入BaseView，再调用其setPresenter方法
    void setPresent(T present);
}
