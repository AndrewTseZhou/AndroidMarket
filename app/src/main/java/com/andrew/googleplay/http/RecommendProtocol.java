package com.andrew.googleplay.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.http
 * @创建者: 谢康
 * @创建时间: 2016/11/22 下午 12:41
 * @描述: TODO
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class RecommendProtocol extends BaseProtocol<List<String>> {
    @Override
    protected String getInterfaceKey() {
        return "recommend";
    }

    @Override
    protected List<String> parseJson(String json) {
        return new Gson().fromJson(json, new TypeToken<List<String>>() {
        }.getType());
    }
}
