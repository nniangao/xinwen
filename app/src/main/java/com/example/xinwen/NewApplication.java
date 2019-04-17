package com.example.xinwen;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


public class NewApplication extends Application {
    private static Context context;
    private static int mainThreadId;
    private static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        // 获取当前主线程id
        mainThreadId = android.os.Process.myTid();
        handler = new Handler();
    }

    public static Context getContext() {
        return context;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

    public static Handler getHandler() {
        return handler;
    }
}
