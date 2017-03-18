package com.andrew.googleplay.holder;

import android.view.View;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.holder
 * @创建者: 谢康
 * @创建时间: 2016/10/15 下午 16:00
 * @描述: item view对应的View的持有者的基类
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public abstract class BaseHolder<T> {
    //提供不具体化的View
    protected View mRootView;//根视图
    protected T    mData;

    public BaseHolder() {
        mRootView = initView();

        //设置标记
        mRootView.setTag(this);
    }

    /**
     * 实现View的布局
     *
     * @return
     */
    protected abstract View initView();

    /**
     * 让子类根据数据来刷新自己的视图
     *
     * @param data
     */
    protected abstract void refreshUI(T data);

    /**
     * 获取根布局
     *
     * @return
     */
    public View getRootView() {
        return mRootView;
    }

    public void setData(T data) {
        //保存数据
        this.mData = data;

        //通过数据来改变UI显示
        refreshUI(data);
    }
}
