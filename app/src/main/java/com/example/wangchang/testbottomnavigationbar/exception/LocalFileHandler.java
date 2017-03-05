package com.example.wangchang.testbottomnavigationbar.exception;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import com.example.wangchang.testbottomnavigationbar.activity.MainActivity;
import com.example.wangchang.testbottomnavigationbar.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;


/**
 * 本地异常处理类
 *
 * @author PLUTO
 */
public class LocalFileHandler extends BaseExceptionHandler {

    private Context context;

    public LocalFileHandler(Context context) {
        this.context = context;
    }

    /**
     * 自定义错误处理,手机错误信息,发送错误报告操作均在此完成,开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return
     */
    @Override
    public boolean handleException(Throwable ex) {
        if (ex == null)
            return false;

        new Thread() {
            public void run() {
                Looper.prepare();
//                restartApp(context);
                Toast.makeText(context, "很抱歉，程序出现异常，正在从错误中恢复", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        //保存错误日志
        saveLog(ex);

        return true;
    }

    private void restartApp(Context context){
        // 杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    /**
     * 保存错误日志到本地
     *
     * @param ex
     */
    private void saveLog(Throwable ex) {
        try {

            File path = new File(FileUtil.getDiskCacheDir(context) + "/log");
            if (!path.exists()) {
                path.mkdirs();
            }

            File errorFile = new File(path + "/crash.txt");

            if (!errorFile.exists()) {
                errorFile.createNewFile();
            }

            OutputStream out = new FileOutputStream(errorFile, true);
            out.write(("\n\n-----错误分割线" + dateFormat.format(new Date()) + "-----\n\n").getBytes());
            PrintStream stream = new PrintStream(out);
            ex.printStackTrace(stream);
            stream.flush();
            out.flush();
            stream.close();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
