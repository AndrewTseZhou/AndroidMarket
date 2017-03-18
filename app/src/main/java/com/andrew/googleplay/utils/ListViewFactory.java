package com.andrew.googleplay.utils;

import android.graphics.Color;
import android.widget.ListView;

import com.andrew.googleplay.R;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.utils
 * @创建者: 谢康
 * @创建时间: 2016/11/9 下午 16:23
 * @描述: TODO
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class ListViewFactory {

    public static ListView getListView(){
        ListView listView = new ListView(UIUtils.getContext());

        // 属性设置
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setSelector(android.R.color.transparent);
        listView.setDividerHeight(0);
        listView.setScrollingCacheEnabled(false);
        listView.setBackgroundColor(UIUtils.getColor(R.color.bg));

        return listView;
    }
}
