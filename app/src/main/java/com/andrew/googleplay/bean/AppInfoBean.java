package com.andrew.googleplay.bean;

import java.util.List;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.bean
 * @创建者: 谢康
 * @创建时间: 2016/10/18 下午 17:29
 * @描述: TODO
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class AppInfoBean {
    public String            des;//应用的描述
    public String            downloadUrl;//应用的下载地址
    public String            iconUrl;//应用的图标地址
    public long              id;//
    public String            name;//应用名称
    public String            packageName;//应用包名
    public long              size;//应用的大小
    public float             stars;//应用的点赞数
    public String            author;//作者
    public String            date;//时间
    public String            downloadNum;//下载次数
    public List<AppSafeBean> safe;
    public List<String>      screen;
    public String            version;//版本号

    public class AppSafeBean {
        public String safeDes;
        public int    safeDesColor;
        public String safeDesUrl;//安全描述图标对应的地址
        public String safeUrl;//安全图标对应的地址
    }
}
