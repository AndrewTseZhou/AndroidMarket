package com.andrew.googleplay.http;

import com.andrew.googleplay.bean.AppInfoBean;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;

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
public class GameProtocol extends BaseProtocol<List<AppInfoBean>> {
    @Override
    protected String getInterfaceKey() {
        return "game";
    }

    @Override
    protected List<AppInfoBean> parseJson(String json) {
        List<AppInfoBean> list = null;

        //新建解析器
        JsonParser parser = new JsonParser();

        //解析json
        JsonElement rootElement = parser.parse(json);

        //将根节点具体化类型
        JsonArray rootArray = rootElement.getAsJsonArray();//获取实际类型

        //遍历array
        for (int i = 0; i < rootArray.size(); i++) {
            AppInfoBean bean        = new AppInfoBean();
            JsonElement itemElement = rootArray.get(i);
            //将节点具体化类型
            JsonObject itemObject = itemElement.getAsJsonObject();
            //获取子节点的元素
            JsonPrimitive desPrimitive = itemObject.getAsJsonPrimitive("des");
            //获取value的值
            bean.des = desPrimitive.getAsString();

            //获取子节点的元素
            JsonPrimitive downloadUrlPrimitive = itemObject.getAsJsonPrimitive("downloadUrl");
            //获取value的值
            bean.downloadUrl = downloadUrlPrimitive.getAsString();

            //获取子节点的元素
            JsonPrimitive iconUrlPrimitive = itemObject.getAsJsonPrimitive("iconUrl");
            //获取value的值
            bean.iconUrl = iconUrlPrimitive.getAsString();

            //获取子节点的元素
            JsonPrimitive idPrimitive = itemObject.getAsJsonPrimitive("id");
            //获取value的值
            bean.id = idPrimitive.getAsLong();

            //获取子节点的元素
            JsonPrimitive namePrimitive = itemObject.getAsJsonPrimitive("name");
            //获取value的值
            bean.name = namePrimitive.getAsString();

            //获取子节点的元素
            JsonPrimitive packageNamePrimitive = itemObject.getAsJsonPrimitive("packageName");
            //获取value的值
            bean.packageName = packageNamePrimitive.getAsString();

            //获取子节点的元素
            JsonPrimitive sizePrimitive = itemObject.getAsJsonPrimitive("size");
            //获取value的值
            bean.size = sizePrimitive.getAsLong();

            //获取子节点的元素
            JsonPrimitive starsPrimitive = itemObject.getAsJsonPrimitive("stars");
            //获取value的值
            bean.stars = starsPrimitive.getAsFloat();

            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(bean);
        }

        return list;
    }
}
