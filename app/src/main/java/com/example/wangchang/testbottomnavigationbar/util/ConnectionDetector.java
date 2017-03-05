package com.example.wangchang.testbottomnavigationbar.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {

	public static boolean isConnection(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		// netInfo == null:当前没有已激活的网络连接（表示用户关闭了数据流量服务，也没有开启WiFi等别的数据服务）
		// 若不等于空，说明有激活的网络连接，用netInfo.isAvailable来判断网络是否可用。
		if (netInfo == null || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	}

}
