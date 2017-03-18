package com.andrew.googleplay.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.andrew.googleplay.R;
import com.andrew.googleplay.utils.BitmapHelper;
import com.andrew.googleplay.utils.Constans;
import com.andrew.googleplay.utils.UIUtils;
import com.andrew.googleplay.widget.RatioLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.holder
 * @创建者: 谢康
 * @创建时间: 2016/11/26 下午 20:15
 * @描述: 应用详情页面中屏幕截图的holder
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class AppDetailPicHolder extends BaseHolder<List<String>> {
    @ViewInject(R.id.app_detail_pic_iv_container)
    private LinearLayout mPicContainer;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_pic, null);

        ViewUtils.inject(this, view);

        return view;
    }

    @Override
    protected void refreshUI(List<String> data) {
        //清空
        mPicContainer.removeAllViews();

        for (int i = 0; i < data.size(); i++)
        {
            RatioLayout ratioLayout = new RatioLayout(UIUtils.getContext());
            ratioLayout.setRatio(0.6f);
            ratioLayout.setRelative(RatioLayout.RELATIVE_WIDTH);

            //动态加载图片数据
            ImageView iv = new ImageView(UIUtils.getContext());
            BitmapHelper.display(iv, Constans.IMAGE_BASE_URL + data.get(i));
            ratioLayout.addView(iv);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2px(80),
                                                                             ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i != 0)
            {
                params.leftMargin = UIUtils.dip2px(8);
            }

            mPicContainer.addView(ratioLayout, params);
        }
    }
}
