package com.andrew.googleplay.utils;

import com.andrew.googleplay.bean.AppInfoBean;
import com.andrew.googleplay.manager.DownloadManager;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.utils
 * @创建者: 谢康
 * @创建时间: 2016/12/6 下午 19:38
 * @描述: 和下载相关的工具类
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class DownloadTools {
    /**
     * 下载
     */
    public static void doDownload(AppInfoBean bean) {
        DownloadManager.getInstance().download(bean);
    }

    /**
     * 暂停下载
     */
    public static void doPause(AppInfoBean bean) {
        DownloadManager.getInstance().pause(bean);
    }

    /**
     * 取消下载
     */
    public static void doCancel(AppInfoBean bean) {
        DownloadManager.getInstance().cancel(bean);
    }

    /**
     * 安装
     */
    public static void doInstall(AppInfoBean bean) {
        DownloadManager.getInstance().install(bean);
    }

    /**
     * 打开
     */
    public static void doOpen(AppInfoBean bean) {
        DownloadManager.getInstance().open(bean);
    }
}
