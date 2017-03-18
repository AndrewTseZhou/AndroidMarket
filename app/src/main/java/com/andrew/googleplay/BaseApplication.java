package com.andrew.googleplay;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay
 * @创建者: 谢康
 * @创建时间: 2016/10/11 下午 20:06
 * @描述: TODO
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class BaseApplication extends Application {
    private static Context mContext;
    private static Thread  mMainThread;
    private static long    mMainThreadId;
    private static Looper  mMainLooper;
    private static Handler mMainHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        //上下文
        mContext = getApplicationContext();
        //主线程
        mMainThread = Thread.currentThread();
        //主线程id
        //mMainThreadId=mMainThread.getId();
        mMainThreadId = android.os.Process.myTid();

        mMainLooper = getMainLooper();
        //创建主线程的handler
        mMainHandler = new Handler();
    }

    public static Context getContext() {
        return mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static Handler getMainHandler() {
        return mMainHandler;
    }
}
