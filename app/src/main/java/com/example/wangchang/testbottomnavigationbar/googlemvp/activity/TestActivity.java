package com.example.wangchang.testbottomnavigationbar.googlemvp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.wangchang.testbottomnavigationbar.R;
import com.example.wangchang.testbottomnavigationbar.googlemvp.model.BeautyModel;
import com.example.wangchang.testbottomnavigationbar.googlemvp.present.TaskPresenter;
import com.example.wangchang.testbottomnavigationbar.googlemvp.view.TaskDetailContract;

import java.util.List;

public class TestActivity extends AppCompatActivity implements TaskDetailContract.View, View.OnClickListener {

    private TaskDetailContract.Present taskDetaiContractPresent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        new TaskPresenter(this);
    }

    @Override
    public void setPresent(TaskDetailContract.Present present) {
        this.taskDetaiContractPresent = present;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showBeauty(List<BeautyModel> beautyList) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.taskDetaiContractPresent.start();
    }

    private void loadBeauty(int pageIndex, int pageSize) {
        if (this.taskDetaiContractPresent != null) {
            this.taskDetaiContractPresent.loadBeauty(pageIndex, pageSize);
        }
    }

    @Override
    public void onClick(View v) {
        loadBeauty(1, 10);
    }
}