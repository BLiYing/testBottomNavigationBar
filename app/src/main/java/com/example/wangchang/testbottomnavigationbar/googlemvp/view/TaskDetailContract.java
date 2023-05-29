package com.example.wangchang.testbottomnavigationbar.googlemvp.view;

import com.example.wangchang.testbottomnavigationbar.googlemvp.model.BeautyModel;
import com.example.wangchang.testbottomnavigationbar.googlemvp.present.BasePresent;

import java.util.List;

/**
 * author : liying
 * e-mail : liying@yuntongxun.com
 * time   : 2023/05/24 09:15
 * desc   : 契约类
 * version: 2.5.0.2
 */
public class TaskDetailContract {
     public interface Present extends BasePresent{
         void loadBeauty(int pageIndex, int pageSize);
     }

     public interface View extends BaseView<Present> {
         void showProgress();
         void hideProgress();
         void showBeauty(List<BeautyModel> beautyList);
     }
}
