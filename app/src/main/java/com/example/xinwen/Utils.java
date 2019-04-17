package com.example.xinwen;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import okhttp3.OkHttpClient;
import okhttp3.Request;


//工具类,

public class Utils {

	public static Context getContext() {
		return NewApplication.getContext();
	}

	public static int getMainThreadId() {
		return NewApplication.getMainThreadId();
	}

	public static Handler getHandler() {
		return NewApplication.getHandler();
	}

	//根据id获取字符串

	public static String getString(int id) {
		return getContext().getResources().getString(id);
	}

	//根据id获取字符串数组

	public static String[] getStringArray(int id) {
		return getContext().getResources().getStringArray(id);
	}

	//加载布局文件

	public static View inflate(int layoutId) {
		return View.inflate(getContext(), layoutId, null);
	}

	//判断当前是否运行在主线程

	public static boolean isRunOnUiThread() {
		return getMainThreadId() == android.os.Process.myTid();
	}
	//网络请求工具

	public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
		OkHttpClient client=new OkHttpClient();
		Request request=new Request.Builder().url(address).build();
		client.newCall(request).enqueue(callback);
	}

	//保证当前的操作运行在UI主线程
	 public static void runOnUiThread(Runnable runnable) {
		if (isRunOnUiThread()) {
			runnable.run();
		} else {
			getHandler().post(runnable);
		}
	}
}
