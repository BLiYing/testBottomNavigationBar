package com.example.wangchang.testbottomnavigationbar.googlemvp.present;

import com.example.wangchang.testbottomnavigationbar.googlemvp.view.TaskDetailContract;

/**
 * author : liying
 * e-mail : liying@yuntongxun.com
 * time   : 2023/05/24 09:19
 * desc   :
 * version: 2.5.0.2
 */
public class TaskPresenter implements TaskDetailContract.Present {

    private TaskDetailContract.View mView;

    public TaskPresenter(TaskDetailContract.View view){
        this.mView = view;
        this.mView.setPresent(this);
    }



    @Override
    public void start() {
        loadBeauty(0, 0);
    }

    @Override
    public void loadBeauty(int pageIndex, int pageSize) {
        //Rxjava
        if (this.mView != null) {
            this.mView.showProgress();
            this.mView.showBeauty(null);
            this.mView.hideProgress();
        }
    }
}
