package com.andrew.googleplay.http;

import com.andrew.googleplay.bean.AppInfoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * @项目名: GooglePlay56
 * @包名: org.andrew.googleplay.http
 * @类名: AppProtocol
 * @创建者: 谢康
 * @创建时间: 2016/11/9 下午 13:54
 * @描述: TODO
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class AppProtocol extends BaseProtocol<List<AppInfoBean>> {

    @Override
    protected String getInterfaceKey() {
        return "app";
    }

    @Override
    protected List<AppInfoBean> parseJson(String json) {
        return new Gson().fromJson(json, new TypeToken<List<AppInfoBean>>() {
        }.getType());
    }

}