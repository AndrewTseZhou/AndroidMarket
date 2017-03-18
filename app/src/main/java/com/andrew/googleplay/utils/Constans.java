package com.andrew.googleplay.utils;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.http
 * @创建者: 谢康
 * @创建时间: 2016/11/9 下午 13:54
 * @描述: TODO
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public interface Constans {
//    String SERVER_URL        = "http://10.0.2.2:8080/GooglePlayServer/";
    String SERVER_URL        = "http://192.168.191.1:8080/GooglePlayServer/";
    String IMAGE_BASE_URL    = SERVER_URL + "image?name=";
    String DOWNLOAD_BASE_URL = SERVER_URL + "download";
    int    PAGER_SIZE        = 10;                                        // 每页显示的数据量
    long   REFRESH_DELAY     = 5 * 60 * 1000;
}
