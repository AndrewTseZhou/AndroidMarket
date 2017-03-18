package com.andrew.googleplay.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.andrew.googleplay.AppDetailActivity;
import com.andrew.googleplay.bean.AppInfoBean;
import com.andrew.googleplay.bean.DownloadInfo;
import com.andrew.googleplay.holder.AppItemHolder;
import com.andrew.googleplay.holder.BaseHolder;
import com.andrew.googleplay.manager.DownloadManager;
import com.andrew.googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.adapter
 * @创建者: 谢康
 * @创建时间: 2016/11/22 下午 14:20
 * @描述: TODO
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class AppListAdapter extends SuperBaseAdapter<AppInfoBean>
        implements DownloadManager.DownloadObserver {
    private List<AppInfoBean> mDatas;
    private List<AppItemHolder> mHolders = new ArrayList<>();

    public AppListAdapter(AbsListView listView, List<AppInfoBean> datas) {
        super(listView, datas);
        this.mDatas = datas;
    }

    @Override
    protected BaseHolder<AppInfoBean> getItemHolder(int position) {
        AppItemHolder holder = new AppItemHolder();
        mHolders.add(holder);

        return holder;
    }

    /**
     * listView的item点击事件
     * 跳转到应用详情页面
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    protected void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        //跳转到应用详情页面
        Context context = UIUtils.getContext();
        Intent  intent  = new Intent(context, AppDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(AppDetailActivity.KEY_PACKAGENAME, mDatas.get(position).packageName);
        context.startActivity(intent);
    }

    public void startObserver() {
        DownloadManager.getInstance().addObserver(this);
        //刷新状态
        checkStates();
    }

    private void checkStates() {
        ListIterator<AppItemHolder> iterator = mHolders.listIterator();
        while (iterator.hasNext())
        {
            iterator.next().checkState();
        }
    }

    private void notifyDownloadStateChanged(DownloadInfo info) {
        ListIterator<AppItemHolder> iterator = mHolders.listIterator();
        while (iterator.hasNext())
        {
            iterator.next().onDownloadStateChanged(info);
        }
    }

    public void stopObserver() {
        DownloadManager.getInstance().deleteObserver(this);
    }

    @Override
    public void onDownloadStateChanged(DownloadManager manager, DownloadInfo info) {
        notifyDownloadStateChanged(info);
    }

    @Override
    public void onDownloadProgressChanged(DownloadManager manager, DownloadInfo info) {
        notifyDownloadStateChanged(info);
    }
}
