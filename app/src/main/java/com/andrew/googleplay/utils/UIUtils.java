package com.andrew.googleplay.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.util.DisplayMetrics;

import com.andrew.googleplay.BaseApplication;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.utils
 * @创建者: 谢康
 * @创建时间: 2016/10/11 下午 20:33
 * @描述: 提供UI操作的工具类
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class UIUtils {

    public static Context getContext() {
        return BaseApplication.getContext();
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    public static String getPackageName() {
        return getContext().getPackageName();
    }

    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    public static Handler getMainHandler() {
        return BaseApplication.getMainHandler();
    }

    public static long getMainThreadId() {
        return BaseApplication.getMainThreadId();
    }

    /**
     * 让task在主线程中执行
     */
    public static void post(Runnable task) {
        //获取当前线程的id
        int myTid = android.os.Process.myTid();

        if (myTid == getMainThreadId())
        {
            //如果task在主线程中执行
            task.run();
        } else
        {
            //如果task在子线程中执行的 就调用post
            getMainHandler().post(task);
        }
    }

    public static int dip2px(int dip) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float          density        = displayMetrics.density;
        return (int) (dip * density + 0.5f);
    }

    public static int px2dip(int px) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float          density        = displayMetrics.density;
        return (int) (px / density + 0.5f);
    }

    /**
     * 轮播图执行延时任务
     *
     * @param task    执行的任务
     * @param delayed 延时的时间
     */
    public static void postDelayed(Runnable task, int delayed) {
        getMainHandler().postDelayed(task, delayed);
    }

    /**
     * 移除轮播任务
     *
     * @param task
     */
    public static void removeCallbacks(Runnable task) {
        getMainHandler().removeCallbacks(task);
    }

    /**
     * 在string.xml文件中定义了占位的样式后调用此方法
     *
     * @param id
     * @param formatArgs
     * @return
     */
    public static String getString(int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }
}
