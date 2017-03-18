package com.andrew.googleplay.http;

import com.andrew.googleplay.bean.AppInfoBean;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.http
 * @创建者: 谢康
 * @创建时间: 2016/11/22 下午 14:48
 * @描述: 应用详情页面的协议
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class AppDetailProtocol extends BaseProtocol<AppInfoBean> {
    private String mPackageName;

    public AppDetailProtocol(String packageName) {
        this.mPackageName = packageName;
    }

    @Override
    protected String getInterfaceKey() {
        return "detail";
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String, String> map = new HashMap<>();
        map.put("packageName", mPackageName);

        return map;
    }

    @Override
    protected AppInfoBean parseJson(String json) {
        return new Gson().fromJson(json, AppInfoBean.class);
    }
}
