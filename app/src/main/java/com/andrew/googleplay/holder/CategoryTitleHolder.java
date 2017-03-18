package com.andrew.googleplay.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.andrew.googleplay.bean.CategoryBean;
import com.andrew.googleplay.utils.UIUtils;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.holder
 * @创建者: 谢康
 * @创建时间: 2016/11/21 下午 14:08
 * @描述: TODO
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class CategoryTitleHolder extends BaseHolder<CategoryBean> {

    private TextView mTv;

    @Override
    protected View initView() {
        mTv = new TextView(UIUtils.getContext());
        mTv.setBackgroundColor(Color.WHITE);
        mTv.setTextColor(Color.GRAY);
        mTv.setPadding(UIUtils.dip2px(5), UIUtils.dip2px(5), UIUtils.dip2px(5), UIUtils.dip2px(5));

        return mTv;
    }

    @Override
    protected void refreshUI(CategoryBean data) {
        mTv.setText(data.title);
    }
}
