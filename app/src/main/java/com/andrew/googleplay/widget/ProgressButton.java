package com.andrew.googleplay.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.widget
 * @创建者: 谢康
 * @创建时间: 2016/11/30 下午 16:17
 * @描述: 带有进度条的button
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class ProgressButton extends Button {
    private boolean  mProgressEnable;
    private long     mProgress;
    private long     mMax;
    private Drawable mProgressDrawable;

    public ProgressButton(Context context) {
        this(context, null);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setProgressEnable(boolean enable) {
        this.mProgressEnable = enable;
    }

    public void setProgress(long progress) {
        this.mProgress = progress;
        invalidate();
    }

    public void setMax(long max) {
        this.mMax = max;
    }

    public void setProgressDrawable(Drawable drawable) {
        this.mProgressDrawable = drawable;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mProgressEnable)
        {
            //画矩形进度条
            int width = getMeasuredWidth();
            int right = 0;
            if (mMax == 0)
            {
                right = (int) (width * mProgress / 100f + 0.5f);
            }
            else
            {
                right = (int) (width * mProgress * 1f / mMax + 0.5f);
            }
            //设置矩形
            mProgressDrawable.setBounds(0, 0, right, getMeasuredHeight());
            //画图形
            mProgressDrawable.draw(canvas);
        }

        super.onDraw(canvas);
    }
}
