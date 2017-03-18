package com.andrew.googleplay.http;

import com.andrew.googleplay.bean.SubjectBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.http
 * @创建者: 谢康
 * @创建时间: 2016/11/17 下午 14:21
 * @描述: TODO
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class SubjectProtocol extends BaseProtocol<List<SubjectBean>> {

    @Override
    protected String getInterfaceKey() {
        return "subject";
    }

    @Override
    protected List<SubjectBean> parseJson(String json) {
        return new Gson().fromJson(json, new TypeToken<List<SubjectBean>>() {
        }.getType());
    }
}
