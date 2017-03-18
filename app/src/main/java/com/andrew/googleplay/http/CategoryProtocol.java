package com.andrew.googleplay.http;

import com.andrew.googleplay.bean.CategoryBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.http
 * @创建者: 谢康
 * @创建时间: 2016/11/20 下午 19:55
 * @描述: 分类页面的网络协议
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class CategoryProtocol extends BaseProtocol<List<CategoryBean>> {
    @Override
    protected String getInterfaceKey() {
        return "category";
    }

    @Override
    protected List<CategoryBean> parseJson(String json) {
        List<CategoryBean> list = null;
        try {
            //原生的json解析
            JSONArray rootArray = new JSONArray(json);

            for (int i = 0; i < rootArray.length(); i++) {
                JSONObject   jsonObject = rootArray.getJSONObject(i);
                CategoryBean titleBean  = new CategoryBean();
                titleBean.isTitle = true;
                //获取title节点的数据
                titleBean.title = jsonObject.getString("title");
                //将titleBean添加到集合中
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(titleBean);

                //获取info节点的数据
                JSONArray infosArray = jsonObject.getJSONArray("infos");
                for (int j = 0; j < infosArray.length(); j++) {
                    JSONObject   infoObject = infosArray.getJSONObject(j);
                    CategoryBean infoBean   = new CategoryBean();

                    infoBean.isTitle = false;
                    infoBean.name1 = infoObject.getString("name1");
                    infoBean.name2 = infoObject.getString("name2");
                    infoBean.name3 = infoObject.getString("name3");
                    infoBean.url1 = infoObject.getString("url1");
                    infoBean.url2 = infoObject.getString("url2");
                    infoBean.url3 = infoObject.getString("url3");

                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(infoBean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }
}
