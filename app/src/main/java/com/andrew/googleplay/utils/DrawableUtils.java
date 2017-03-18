package com.andrew.googleplay.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.utils
 * @创建者: 谢康
 * @创建时间: 2016/11/20 上午 09:26
 * @描述: Drawable的工具类
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class DrawableUtils {

    public static GradientDrawable getShape(int shape, int radius, int argb) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(shape);//设置形状
        gd.setCornerRadius(radius);//设置圆角
        gd.setColor(argb);//设置颜色

        return gd;
    }

    public static StateListDrawable getSelector(Drawable normalBg, Drawable pressedBg) {
        StateListDrawable selector = new StateListDrawable();
        selector.addState(new int[]{android.R.attr.state_pressed}, pressedBg);//必须放在前面 否则点击没有效果
        selector.addState(new int[]{}, normalBg);

        return selector;
    }
}
