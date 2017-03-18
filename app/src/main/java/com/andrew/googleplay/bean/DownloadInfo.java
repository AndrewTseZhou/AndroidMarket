package com.andrew.googleplay.bean;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.bean
 * @创建者: 谢康
 * @创建时间: 2016/12/4 上午 10:51
 * @描述: 下载的信息
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class DownloadInfo {
    public String   downloadUrl;
    public String   savePath;
    public int      download_state;//下载的状态
    public long     progress;//下载的进度
    public long     size;//应用的大小
    public String   packageName;//当前下载的应用的包名
    public Runnable task;//用于记录下载的任务
}
