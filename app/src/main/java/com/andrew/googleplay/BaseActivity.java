package com.andrew.googleplay;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import cn.sharesdk.framework.ShareSDK;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay
 * @创建者: 谢康
 * @创建时间: 2016/11/27 下午 19:09
 * @描述: Activity的基类
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public abstract class BaseActivity extends AppCompatActivity {
    //对所有的activity进行管理
    private static List<Activity> mActivities = new LinkedList<>();
    private static Activity mCurrentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        synchronized (mActivities)
        {
            mActivities.add(this);
        }

        //初始化View
        initView();
        //初始化ToolBar
        initToolbar();
        //初始化Data
        initData();
        //初始化shareSDK
        ShareSDK.initSDK(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        synchronized (mActivities)
        {
            mActivities.remove(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
    }

    /**
     * 安全地退出应用
     */
    public static void exitApp() {
        ListIterator<Activity> iterator = mActivities.listIterator();
        while (iterator.hasNext())
        {
            Activity activity = iterator.next();
            activity.finish();
        }
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    protected abstract void initView();

    protected abstract void initToolbar();

    protected abstract void initData();
}
