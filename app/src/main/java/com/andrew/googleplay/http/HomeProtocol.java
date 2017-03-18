package com.andrew.googleplay.http;


import com.andrew.googleplay.bean.HomeBean;
import com.google.gson.Gson;

/**
 * @项目名: GooglePlay
 * @包名: org.andrew.googleplay.http
 * @创建者: 谢康
 * @创建时间: 2016/11/9 下午 13:54
 * @描述: TODO
 * * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class HomeProtocol extends BaseProtocol<HomeBean> {

    @Override
    protected String getInterfaceKey() {
        return "home";
    }

    @Override
    protected HomeBean parseJson(String json) {
        return new Gson().fromJson(json, HomeBean.class);
    }

}
