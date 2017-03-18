package com.andrew.googleplay.manager;

import com.andrew.googleplay.bean.AppInfoBean;
import com.andrew.googleplay.bean.DownloadInfo;
import com.andrew.googleplay.utils.CommonUtils;
import com.andrew.googleplay.utils.Constans;
import com.andrew.googleplay.utils.FileUtils;
import com.andrew.googleplay.utils.IOUtils;
import com.andrew.googleplay.utils.UIUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.manager
 * @创建者: 谢康
 * @创建时间: 2016/11/30 下午 16:53
 * @描述: 下载操作的管理
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class DownloadManager {
    //下载的各种状态说明
    //未下载	 下载	 去下载
    //下载中	显示进度条	 去暂停下载
    //等待	等待中...	 取消下载
    //暂停	继续下载	 去下载
    //下载成功	安装	 去安装
    //下载失败	重试	 去下载
    //已安装	打开	 去启动
    public static final int STATE_UNDOWNLOAD  = 0;//未下载
    public static final int STATE_DOWNLOADING = 1;//下载中
    public static final int STATE_WAITING     = 2;//等待
    public static final int STATE_PAUSE       = 3;//暂停
    public static final int STATE_DOWNLOADED  = 4;//已经下载完成 但是未安装
    public static final int STATE_FAILED      = 5;//下载失败
    public static final int STATE_INSTALLED   = 6;//已安装

    private static DownloadManager instance;
    private        ThreadPoolProxy mDownloadPool;//下载的线程池
    private        HttpUtils       mHttpUtils;
    //用来记录下载的信息
    private Map<String, DownloadInfo> mDownloadInfos = new HashMap<>();
    private List<DownloadObserver>    mObservers     = new ArrayList<>();

    private DownloadManager() {
        this.mDownloadPool = ThreadManager.getDownloadPool();
        mHttpUtils = new HttpUtils();
    }

    /**
     * 获得DownloadManager的实例
     *
     * @return
     */
    public static DownloadManager getInstance() {
        if (instance == null)
        {
            synchronized (DownloadManager.class)
            {
                if (instance == null)
                {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    /**
     * 获得下载状态的信息
     *
     * @param bean
     * @return
     */
    public DownloadInfo getDownloadInfo(AppInfoBean bean) {
        //判断应用程序是否安装
        if (CommonUtils.isInstalled(UIUtils.getContext(), bean.packageName))
        {
            //安装状态
            DownloadInfo info = generateDownloadInfo(bean);
            info.download_state = STATE_INSTALLED;
//            notifyDownloadStateChanged(info);
            return info;
        }

        //已经下载了 但是没有安装
        File apkFile = getApkFile(bean.packageName);
        if (apkFile.exists())
        {
            if (apkFile.length() == bean.size)
            {
                //缓存目录里有这个apk 大小要一致
                DownloadInfo info = generateDownloadInfo(bean);
                info.download_state = STATE_DOWNLOADED;
//                notifyDownloadStateChanged(info);
                return info;
            }
        }

        //既没有下载 也没有安装
        DownloadInfo info = mDownloadInfos.get(bean.packageName);
        if (info == null || !apkFile.exists())
        {
            //还没有下载
            info = generateDownloadInfo(bean);
            info.download_state = STATE_UNDOWNLOAD;
//            notifyDownloadStateChanged(info);
            return info;
        }
        else
        {
            //正在下载
//            info = generateDownloadInfo(bean);
//            info.download_state = STATE_DOWNLOADING;
//            notifyDownloadStateChanged(info);
            return info;
        }
    }

    /**
     * 初始化下载状态信息
     *
     * @param bean
     * @return
     */
    public DownloadInfo generateDownloadInfo(AppInfoBean bean) {
        DownloadInfo info = new DownloadInfo();
        info.downloadUrl = bean.downloadUrl;
        info.savePath = getApkFile(bean.packageName).getAbsolutePath();
        info.size = bean.size;
        info.packageName = bean.packageName;

        return info;
    }

    /**
     * 获得apkfile的操作
     *
     * @param packageName
     * @return
     */
    public File getApkFile(String packageName) {
        String dir = FileUtils.getDir("download");
        return new File(dir, packageName + ".apk");
    }

    /**
     * 下载应用
     *
     * @param bean
     */
    public void download(AppInfoBean bean) {
        //开线程下载
        DownloadInfo info = generateDownloadInfo(bean);

        //状态的变化：未下载
        info.download_state = STATE_UNDOWNLOAD;
        notifyDownloadStateChanged(info);

        //状态的变化：等待状态
        info.download_state = STATE_WAITING;
        notifyDownloadStateChanged(info);

        DownloadTask task = new DownloadTask(info);
        info.task = task;

        //记录下载的信息
        mDownloadInfos.put(bean.packageName, info);

        mDownloadPool.excute(task);//将任务添加到任务队列中
    }

    /**
     * 下载任务
     */
    class DownloadTask implements Runnable {
        DownloadInfo mInfo;

        public DownloadTask(DownloadInfo info) {
            this.mInfo = info;
        }

        @Override
        public void run() {
            //状态的变化：下载中
            mInfo.download_state = STATE_DOWNLOADING;
            notifyDownloadStateChanged(mInfo);

            InputStream      is  = null;
            FileOutputStream fos = null;
            try
            {
                File saveFile = new File(mInfo.savePath);
                long range    = 0;
                if (saveFile.exists())
                {
                    //如果文件存在
                    range = saveFile.length();
                }

                //实现下载功能
                String        url    = Constans.DOWNLOAD_BASE_URL;
                RequestParams params = new RequestParams();
                params.addQueryStringParameter("name", mInfo.downloadUrl);
                params.addQueryStringParameter("range", "" + range);//断点下载

                ResponseStream responseStream = mHttpUtils.sendSync(HttpRequest.HttpMethod.GET,
                                                                    url,
                                                                    params);
                is = responseStream.getBaseStream();
                fos = new FileOutputStream(saveFile, true);

                byte[]  buffer   = new byte[1024];
                int     len      = -1;
                long    progress = range;
                boolean isPaused = false;

                while ((len = is.read(buffer)) != -1)
                {
                    //将缓冲区内容写入文件
                    fos.write(buffer, 0, len);
                    fos.flush();

                    //下载进度的信息
                    progress += len;
                    mInfo.progress = progress;
                    notifyDownloadProgressChanged(mInfo);

                    if (mInfo.download_state == STATE_PAUSE)
                    {
                        isPaused = true;
                        break;
                    }

                    if (mInfo.size == progress)
                    {
                        break;
                    }
                }

                if (isPaused)
                {
                    //状态的变化：暂停下载
                    mInfo.download_state = STATE_PAUSE;
                    notifyDownloadStateChanged(mInfo);
                }
                else
                {
                    //状态的变化：下载成功
                    mInfo.download_state = STATE_DOWNLOADED;
                    notifyDownloadStateChanged(mInfo);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                //状态的变化：下载失败
                mInfo.download_state = STATE_FAILED;
                notifyDownloadStateChanged(mInfo);
            }
            finally
            {
                IOUtils.close(is);
                IOUtils.close(fos);
            }
        }
    }

    /**
     * 暂停下载
     *
     * @param bean
     */
    public void pause(AppInfoBean bean) {
        DownloadInfo info = mDownloadInfos.get(bean.packageName);
        if (info == null)
        {
            return;
        }
        info.download_state = STATE_PAUSE;
        notifyDownloadStateChanged(info);
    }

    /**
     * 取消下载
     *
     * @param bean
     */
    public void cancel(AppInfoBean bean) {
        DownloadInfo info = mDownloadInfos.get(bean.packageName);
        if (info == null)
        {
            return;
        }
        if (info.task != null)
        {
            mDownloadPool.remove(info.task);
        }

        info.download_state = STATE_UNDOWNLOAD;
        info.task = null;
        notifyDownloadStateChanged(info);
    }

    /**
     * 安装应用
     *
     * @param bean
     */
    public void install(AppInfoBean bean) {
        File apkFile = getApkFile(bean.packageName);
        if (!apkFile.exists())
        {
            return;
        }
        CommonUtils.installApp(UIUtils.getContext(), apkFile);

        DownloadInfo info = mDownloadInfos.get(bean.packageName);
        if (info == null)
        {
            return;
        }
        info.download_state = STATE_INSTALLED;
        notifyDownloadStateChanged(info);
    }

    /**
     * 打开应用
     *
     * @param bean
     */
    public void open(AppInfoBean bean) {
        CommonUtils.openApp(UIUtils.getContext(), bean.packageName);
    }


    /**
     * 下载状态的观察者
     */
    public interface DownloadObserver {
        /**
         * 当下载状态改变时回调
         *
         * @param manager
         * @param info
         */
        void onDownloadStateChanged(DownloadManager manager, DownloadInfo info);

        /**
         * 当进度改变时的回调
         *
         * @param manager
         * @param info
         */
        void onDownloadProgressChanged(DownloadManager manager, DownloadInfo info);
    }

    /**
     * 添加观察者
     *
     * @param observer
     */
    public synchronized void addObserver(DownloadObserver observer) {
        if (observer == null)
        {
            throw new NullPointerException();
        }
        if (!mObservers.contains(observer))
        {
            mObservers.add(observer);
        }
    }

    /**
     * 删除观察者
     *
     * @param observer
     */
    public synchronized void deleteObserver(DownloadObserver observer) {
        mObservers.remove(observer);
    }

    /**
     * 通知观察者下载状态改变
     *
     * @param info
     */
    public void notifyDownloadStateChanged(DownloadInfo info) {
        ListIterator<DownloadObserver> iterator = mObservers.listIterator();
        while (iterator.hasNext())
        {
            DownloadObserver observer = iterator.next();
            observer.onDownloadStateChanged(this, info);
        }
    }

    /**
     * 通知观察者下载进度的改变
     *
     * @param info
     */
    public void notifyDownloadProgressChanged(DownloadInfo info) {
        ListIterator<DownloadObserver> iterator = mObservers.listIterator();
        while (iterator.hasNext())
        {
            DownloadObserver observer = iterator.next();
            observer.onDownloadProgressChanged(this, info);
        }
    }
}
