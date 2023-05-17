package com.example.wangchang.testbottomnavigationbar.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wangchang.testbottomnavigationbar.R;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class WelcomeActivity extends AppCompatActivity {
    /**
     * 权限判断
     */
    private static final int REQUECT_CODE_SDCARD = 2;
    private final static String locationPermission[] = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * 是否有读取文件权限
     *
     * @return true表示有
     */
    private boolean hasCompletePermission() {

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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUECT_CODE_SDCARD)
    public void requestSdcardSuccess() {
        if (EasyPermissions.hasPermissions(this, locationPermission)) {
            // Already have permission, do the thing
            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
        } else {
            // Do not have permissions, request them now
            requestSdcardFailed();

        }
    }

    public void requestSdcardFailed() {
        showdialog();
    }


    private Dialog warningdialog;

    public void showdialog() {
        if (warningdialog != null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
        builder.setCancelable(false);
        warningdialog = builder.setTitle(R.string.excusme)
                .setMessage(R.string.excusme_content)
                .setPositiveButton(R.string.excusme_sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EasyPermissions.requestPermissions(WelcomeActivity.this, getString(R.string.excusme_content), REQUECT_CODE_SDCARD, Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
        setContentView(R.layout.activity_welcome);
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            EasyPermissions.requestPermissions(WelcomeActivity.this, getString(R.string.excusme_content), REQUECT_CODE_SDCARD, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            requestSdcardSuccess();

        }
    }
}
