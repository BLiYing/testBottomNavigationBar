package com.example.wangchang.testbottomnavigationbar.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * ToastUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-12-9
 */
public class ToastUtils {
	
	private static Toast mToast;

    private ToastUtils() {
        throw new AssertionError();
    }
    public static void show(Context context, int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getResources().getText(resId), duration);
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

	public static void show(Context context, CharSequence text, int duration) {
		
        //Toast.makeText(context, text, duration).show();
		if (mToast == null) {
			mToast = Toast.makeText(context, text, duration);
			mToast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			mToast.setText(text);
			mToast.setDuration(duration);
		}
		mToast.show();
    }
	
	/**取消吐丝
	 * 
	 */
	public static void cancelToast() {
		if (mToast != null) {
			mToast.cancel();
		}
	}

    public static void show(Context context, int resId, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String format, Object... args) {
        show(context, String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), duration);
    }

    public static void show(Context context, String format, int duration, Object... args) {
        show(context, String.format(format, args), duration);
    }
}
