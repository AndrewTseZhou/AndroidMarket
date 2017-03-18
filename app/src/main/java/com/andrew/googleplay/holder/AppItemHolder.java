package com.andrew.googleplay.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.andrew.googleplay.R;
import com.andrew.googleplay.bean.AppInfoBean;
import com.andrew.googleplay.bean.DownloadInfo;
import com.andrew.googleplay.manager.DownloadManager;
import com.andrew.googleplay.utils.BitmapHelper;
import com.andrew.googleplay.utils.Constans;
import com.andrew.googleplay.utils.DownloadTools;
import com.andrew.googleplay.utils.StringUtils;
import com.andrew.googleplay.utils.UIUtils;
import com.andrew.googleplay.widget.ProgressCircleView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

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
 * @创建时间: 2016/10/18 下午 16:17
 * @描述: 首页，应用，游戏页面listView对应的item的holder
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class AppItemHolder extends BaseHolder<AppInfoBean> implements View.OnClickListener {
    //T --> item对应的数据
    @ViewInject(R.id.item_appinfo_iv_icon)
    private ImageView          mIvIcon;
    @ViewInject(R.id.item_appinfo_rb_stars)
    private RatingBar          mRbStar;
    @ViewInject(R.id.item_appinfo_tv_des)
    private TextView           mTvDes;
    @ViewInject(R.id.item_appinfo_tv_size)
    private TextView           mTvSize;
    @ViewInject(R.id.item_appinfo_tv_name)
    private TextView           mTvName;
    @ViewInject(R.id.item_appinfo_pcv_progress)
    private ProgressCircleView mCircleView;
    private DownloadInfo       mInfo;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_info, null);

        // 注入
        ViewUtils.inject(this, view);

        mCircleView.setOnClickListener(this);

        return view;
    }

    @Override
    protected void refreshUI(AppInfoBean data) {
        // 给View设置数据
        mTvDes.setText(data.des);
        mTvSize.setText(StringUtils.formatFileSize(data.size));
        mTvName.setText(data.name);
        mRbStar.setRating(data.stars);
        mIvIcon.setImageResource(R.mipmap.ic_default);// 设置默认值

        // 去网络获取
        BitmapHelper.display(mIvIcon, Constans.IMAGE_BASE_URL + data.iconUrl);

        //检查应用状态
        checkState();
    }

    public void checkState() {
        //获得应用下载状态的信息
        mInfo = DownloadManager.getInstance().getDownloadInfo(mData);

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
        int argb=0;
        switch (state)
        {
            case STATE_UNDOWNLOAD:
                mCircleView.setProgressText("下载");
                mCircleView.setProgressIcon(R.mipmap.ic_download);
                mCircleView.setProgress(0);
                break;
            case STATE_DOWNLOADING:
                mCircleView.setProgressEnable(true);
                mCircleView.setProgress(mInfo.progress);
                mCircleView.setMax(mInfo.size);
                int progress = (int) (mInfo.progress * 100f / mInfo.size + 0.5f);
                //DecimalFormat df = new DecimalFormat(".#");
                mCircleView.setProgressText(progress + "%");
                mCircleView.setProgressIcon(R.mipmap.ic_pause);
                argb = Color.argb(0xff, 30, 148, 254);
                mCircleView.setARGB(argb);
                break;
            case STATE_WAITING:
                mCircleView.setProgressText("等待中...");
                mCircleView.setProgressIcon(R.mipmap.ic_pause);
                break;
            case STATE_PAUSE:
                mCircleView.setProgressEnable(true);
                mCircleView.setProgress(mInfo.progress);
                mCircleView.setMax(mInfo.size);
                mCircleView.setProgressText("继续下载");
                mCircleView.setProgressIcon(R.mipmap.ic_resume);
                argb = Color.argb(0xff, 251, 166, 45);
                mCircleView.setARGB(argb);
                break;
            case STATE_DOWNLOADED:
                mCircleView.setProgressText("安装");
                mCircleView.setProgressIcon(R.mipmap.ic_install);
                mCircleView.setProgress(0);
                break;
            case STATE_FAILED:
                mCircleView.setProgressText("点击重试");
                mCircleView.setProgressIcon(R.mipmap.ic_redownload);
                mCircleView.setProgress(0);
                break;
            case STATE_INSTALLED:
                mCircleView.setProgressText("打开");
                mCircleView.setProgressIcon(R.mipmap.ic_install);
                mCircleView.setProgress(0);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mCircleView)
        {
            clickProgressButton();
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

    public void onDownloadStateChanged(DownloadInfo info) {
        if (info.packageName.equals(mData.packageName))
        {
            //在子线程中执行
            this.mInfo = info;
            //refreshState();//直接调用此方法会导致在子线程中更新UI
            safeRefreshState();
        }
    }
}
