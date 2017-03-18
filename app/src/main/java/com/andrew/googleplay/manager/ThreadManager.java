package com.andrew.googleplay.manager;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.manager
 * @创建者: 谢康
 * @创建时间: 2016/10/15 下午 12:04
 * @描述: 线程池的管理
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class ThreadManager {
    //耗时操作的线程池
    private static ThreadPoolProxy mLongPool;
    private static Object mLongLock = new Object();
    //下载操作的线程池
    private static ThreadPoolProxy mDownloadPool;
    private static Object mDownloadLock = new Object();

    /**
     * 获得耗时操作的线程池
     *
     * @return
     */
    public static ThreadPoolProxy getLongPool() {
        if (mLongPool == null)
        {
            synchronized (mLongLock)
            {
                if (mLongPool == null)
                {
                    mLongPool = new ThreadPoolProxy(5, 5, 0);
                }
            }
        }

        return mLongPool;
    }

    /**
     * 获得下载操作的线程池
     *
     * @return
     */
    public static ThreadPoolProxy getDownloadPool() {
        if (mDownloadPool == null)
        {
            synchronized (mDownloadLock)
            {
                if (mDownloadPool == null)
                {
                    mDownloadPool = new ThreadPoolProxy(3, 3, 0);
                }
            }
        }

        return mDownloadPool;
    }
}
