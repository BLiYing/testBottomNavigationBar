package com.example.wangchang.testbottomnavigationbar.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.wangchang.testbottomnavigationbar.R;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;
import com.zhy.m.permission.ShowRequestPermissionRationale;


public class WelcomeActivity extends AppCompatActivity {
    /**
     * 权限判断
     */
    private static final int REQUECT_CODE_SDCARD = 2;
    private static final long delay_time = 3000;
    private final static String locationPermission[] = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * 是否有读取文件权限
     *
     * @return true表示有
     */
    private boolean hasCompleteLOCATION() {
        // TODO Auto-generated method stub

        PackageManager pm = this.getPackageManager();
        for (String auth : locationPermission) {
            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(REQUECT_CODE_SDCARD)
    public void requestSdcardSuccess() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();
            }
        },delay_time);

    }

    @PermissionDenied(REQUECT_CODE_SDCARD)
    public void requestSdcardFailed() {
        if (!hasCompleteLOCATION()) {
            showdialog();
            if (!MPermissions.shouldShowRequestPermissionRationale(WelcomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUECT_CODE_SDCARD)) {
                MPermissions.requestPermissions(WelcomeActivity.this, REQUECT_CODE_SDCARD, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }
    }


    private Dialog warningdialog;

    @ShowRequestPermissionRationale(REQUECT_CODE_SDCARD)
    public void showdialog() {
        if (warningdialog != null) {
            warningdialog.dismiss();
            warningdialog = null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
        builder.setCancelable(false);
        warningdialog = builder.setTitle(R.string.excusme)
                .setMessage(R.string.excusme_content)
                .setPositiveButton(R.string.excusme_sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        warningdialog = null;
                        MPermissions.requestPermissions(WelcomeActivity.this, REQUECT_CODE_SDCARD, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                })
                .setNegativeButton(R.string.excusme_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        return;
                    }
                })
                .create();
        warningdialog.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置无标题
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置全屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_welcome);
        /**
         * 完全沉浸式
         */
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
           actionBar.hide();

        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasCompleteLOCATION()) {
                MPermissions.requestPermissions(WelcomeActivity.this, REQUECT_CODE_SDCARD,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
                requestSdcardSuccess();

            }
        } else {
            requestSdcardSuccess();

        }
    }
}
