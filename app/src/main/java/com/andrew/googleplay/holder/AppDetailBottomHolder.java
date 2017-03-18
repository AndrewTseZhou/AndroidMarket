package com.andrew.googleplay.holder;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrew.googleplay.R;
import com.andrew.googleplay.bean.AppInfoBean;
import com.andrew.googleplay.bean.DownloadInfo;
import com.andrew.googleplay.manager.DownloadManager;
import com.andrew.googleplay.utils.DownloadTools;
import com.andrew.googleplay.utils.ShareUtils;
import com.andrew.googleplay.utils.UIUtils;
import com.andrew.googleplay.widget.ProgressButton;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.DecimalFormat;

import static com.andrew.googleplay.manager.DownloadManager.STATE_DOWNLOADED;
import static com.andrew.googleplay.manager.DownloadManager.STATE_DOWNLOADING;
import static com.andrew.googleplay.manager.DownloadManager.STATE_FAILED;
import static com.andrew.googleplay.manager.DownloadManager.STATE_INSTALLED;
import static com.andrew.googleplay.manager.DownloadManager.STATE_PAUSE;
import static com.andrew.googleplay.manager.DownloadManager.STATE_UNDOWNLOAD;
import static com.andrew.googleplay.manager.DownloadManager.STATE_WAITING;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.holder
 * @创建者: 谢康
 * @创建时间: 2016/11/30 下午 14:06
 * @描述: 应用详情页面底部holder
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class AppDetailBottomHolder extends BaseHolder<AppInfoBean>
        implements View.OnClickListener, DownloadManager.DownloadObserver {
    @ViewInject(R.id.app_detail_bottom_btn_favo)
    private Button         mBtnFav;
    @ViewInject(R.id.app_detail_bottom_btn_download)
    private ProgressButton mProgressButton;
    @ViewInject(R.id.app_detail_bottom_btn_share)
    private Button         mBtnShare;
    private DownloadInfo   mInfo;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_bottom, null);

        ViewUtils.inject(this, view);

        mBtnFav.setOnClickListener(this);
        mBtnShare.setOnClickListener(this);
        mProgressButton.setOnClickListener(this);
        mProgressButton.setProgressDrawable(new ColorDrawable(Color.argb(0xff, 81, 172, 254)));

        return view;
    }

    @Override
    protected void refreshUI(AppInfoBean data) {
        //给用户的提示
        mInfo = DownloadManager.getInstance().getDownloadInfo(data);

        //根据下载信息给用户的提示
        safeRefreshState();
    }

    private void safeRefreshState() {
        UIUtils.post(new Runnable() {
            @Override
            public void run() {
                refreshState();
            }
        });
    }

    /**
     * 更新状态
     */
    private void refreshState() {
        int state = mInfo.download_state;
        if (state == STATE_DOWNLOADING || state == STATE_PAUSE)
        {
            mProgressButton.setBackgroundResource(R.drawable.progress_downloading_bg);
        }
        else
        {
            mProgressButton.setBackgroundResource(R.drawable.progress_loading_normal_bg);
        }

        switch (state)
        {
            case STATE_UNDOWNLOAD:
                mProgressButton.setText("下载");
                break;
            case STATE_DOWNLOADING:
                mProgressButton.setProgressEnable(true);
                mProgressButton.setProgress(mInfo.progress);
                mProgressButton.setMax(mInfo.size);
                int progress = (int) (mInfo.progress * 100f / mInfo.size + 0.5f);
                DecimalFormat df = new DecimalFormat(".#");
                mProgressButton.setText(progress + "%");
//                mProgressButton.setBackgroundResource(R.drawable.progress_downloading_bg);
                break;
            case STATE_WAITING:
                mProgressButton.setText("等待中...");
                break;
            case STATE_PAUSE:
                mProgressButton.setProgressEnable(true);
                mProgressButton.setProgress(mInfo.progress);
                mProgressButton.setMax(mInfo.size);
                mProgressButton.setText("继续下载");
//                mProgressButton.setBackgroundResource(R.drawable.progress_downloading_bg);
                break;
            case STATE_DOWNLOADED:
                mProgressButton.setText("安装");
                break;
            case STATE_FAILED:
                mProgressButton.setText("点击重试");
                break;
            case STATE_INSTALLED:
                mProgressButton.setText("打开");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.app_detail_bottom_btn_favo:
                Toast.makeText(UIUtils.getContext(), "点击了收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.app_detail_bottom_btn_download:
                clickProgressButton();
                break;
            case R.id.app_detail_bottom_btn_share:
                //分享
                ShareUtils.showShare(UIUtils.getContext());
                break;
        }
    }

    /**
     * 点击了下载位置的按钮
     */
    private void clickProgressButton() {
        int state = mInfo.download_state;

        switch (state)
        {
            case STATE_UNDOWNLOAD:
//                doDownload();
                DownloadTools.doDownload(mData);
                break;
            case STATE_DOWNLOADING:
//                doPause();
                DownloadTools.doPause(mData);
                break;
            case STATE_WAITING:
//                doCancel();
                DownloadTools.doCancel(mData);
                break;
            case STATE_PAUSE:
//                doDownload();
                DownloadTools.doDownload(mData);
                break;
            case STATE_DOWNLOADED:
//                doInstall();
                DownloadTools.doInstall(mData);
                break;
            case STATE_FAILED:
//                doDownload();
                DownloadTools.doDownload(mData);
                break;
            case STATE_INSTALLED:
//                doOpen();
                DownloadTools.doOpen(mData);
                break;
        }
    }

    /**
     * 下载
     */
    private void doDownload() {
        DownloadManager.getInstance().download(mData);
    }

    /**
     * 暂停下载
     */
    private void doPause() {
        DownloadManager.getInstance().pause(mData);
    }

    /**
     * 取消下载
     */
    private void doCancel() {
        DownloadManager.getInstance().cancel(mData);
    }

    /**
     * 安装
     */
    private void doInstall() {
        DownloadManager.getInstance().install(mData);
    }

    /**
     * 打开
     */
    private void doOpen() {
        DownloadManager.getInstance().open(mData);
    }

    @Override
    public void onDownloadStateChanged(DownloadManager manager, DownloadInfo info) {
        if (info.packageName.equals(mData.packageName))
        {
            //在子线程中执行
            this.mInfo = info;
            //refreshState();//直接调用此方法会导致在子线程中更新UI
            safeRefreshState();
        }
    }

    @Override
    public void onDownloadProgressChanged(DownloadManager manager, DownloadInfo info) {
        if (info.packageName.equals(mData.packageName))
        {
            //在子线程中执行
            this.mInfo = info;
            safeRefreshState();
        }
    }

    public void startObserver() {
        DownloadManager.getInstance().addObserver(this);
        refreshUI(mData);
    }

    public void stopObserver() {
        DownloadManager.getInstance().deleteObserver(this);
    }
}
