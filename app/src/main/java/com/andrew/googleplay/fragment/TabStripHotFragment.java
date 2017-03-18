package com.andrew.googleplay.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.andrew.googleplay.http.HotProtocol;
import com.andrew.googleplay.utils.DrawableUtils;
import com.andrew.googleplay.utils.UIUtils;
import com.andrew.googleplay.widget.FlowLayout;

import java.util.List;
import java.util.Random;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.fragment
 * @创建者: 谢康
 * @创建时间: 2016/11/19 下午 16:23
 * @描述: 排行页面
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class TabStripHotFragment extends TabStripBaseFragment {
    private List<String> mDatas;
    private HotProtocol  mProtocol;

    @Override
    protected View onLoadSuccessView() {
        ScrollView scrollView = new ScrollView(UIUtils.getContext());

        FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());
        flowLayout.setSpace(UIUtils.dip2px(10), UIUtils.dip2px(10));
        flowLayout.setPadding(UIUtils.dip2px(20),
                              UIUtils.dip2px(20),
                              UIUtils.dip2px(20),
                              UIUtils.dip2px(20));
        scrollView.addView(flowLayout);

        //给流式布局加载数据
        for (int i = 0; i < mDatas.size(); i++) {
            String data = mDatas.get(i);

            TextView tv = new TextView(UIUtils.getContext());
            tv.setText(data);
            tv.setTextSize(UIUtils.dip2px(20));
            tv.setTextColor(Color.WHITE);
            tv.setGravity(Gravity.CENTER);
            int padding = UIUtils.dip2px(4);
            tv.setPadding(padding, padding, padding, padding);
            tv.setClickable(true);

            Random random = new Random();
            int    shape  = GradientDrawable.RECTANGLE;
            int    radius = UIUtils.dip2px(4);
            int    alpha  = 0xff;//不透明
            int    red    = random.nextInt(200) + 30;
            int    green  = random.nextInt(200) + 30;
            int    blue   = random.nextInt(200) + 30;
            int    argb   = Color.argb(alpha, red, green, blue);

            //GradientDrawable 和XML文件的shape相对应
            //获得默认时的样式
            GradientDrawable normalBg = DrawableUtils.getShape(shape, radius, argb);
            //获得按下时的样式
            GradientDrawable pressedBg = DrawableUtils.getShape(shape, radius, Color.GRAY);

            //StateListDrawable 和XML文件的selector相对应
            StateListDrawable selector = DrawableUtils.getSelector(normalBg, pressedBg);

            tv.setBackground(selector);
            flowLayout.addView(tv);
        }

        return scrollView;
    }

    @Override
    protected LoadingPager.LoadResult onLoadingData() {
        mProtocol = new HotProtocol();
        try {
            mDatas = mProtocol.loadData(0);
            return checkData(mDatas);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }
    }
}
