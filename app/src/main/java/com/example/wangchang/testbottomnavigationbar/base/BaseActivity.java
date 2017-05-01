package com.example.wangchang.testbottomnavigationbar.base;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.wangchang.testbottomnavigationbar.R;

import rx.subjects.PublishSubject;

/**
 * Created by helin on 2016/11/11 10:41.
 */

public class BaseActivity extends AppCompatActivity {

    public final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject = PublishSubject.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE);
//        initSystemBar();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onStop() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY);
    }

    /*
    * 改变状态栏
    */
    @TargetApi(19)
    public void initSystemBar() {
        if (android.os.Build.VERSION.SDK_INT >= 19) {

            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            // 透明状态栏
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // 打开系统状态栏控制
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.page_bg);// 设置背景

        }
    }
}
