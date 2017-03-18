package com.andrew.googleplay.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andrew.googleplay.R;
import com.andrew.googleplay.utils.UIUtils;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.widget
 * @创建者: 谢康
 * @创建时间: 2016/12/6 下午 18:33
 * @描述: TODO
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class ProgressCircleView extends LinearLayout {
    private ImageView mIvIcon;
    private TextView  mTvText;
    private boolean   mProgressEnable;
    private long      mProgress;
    private long      mMax;
    private int       mARGB;
    private RectF     oval;
    private Paint paint = new Paint();

    public ProgressCircleView(Context context) {
        this(context, null);
    }

    public ProgressCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);//凡是继承FrameLayout，LinearLayout等View类 必须在构造函数中加入此句

        View.inflate(getContext(), R.layout.progress_circle_view, this);//这里必须是设置为this，否则没有效果

        mIvIcon = (ImageView) findViewById(R.id.progress_icon);
        mTvText = (TextView) findViewById(R.id.progress_text);
    }

    public void setProgressText(String text) {
        mTvText.setText(text);
    }

    public void setProgressIcon(int resId) {
        mIvIcon.setImageResource(resId);
    }

    public void setProgressEnable(boolean enable) {
        this.mProgressEnable = enable;
        //invalidate();
    }

    public void setProgress(long progress) {
        this.mProgress = progress;
        invalidate();
    }

    public void setMax(long max) {
        this.mMax = max;
    }

    public void setARGB(int argb) {
        this.mARGB = argb;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mProgressEnable)
        {
            //画圆形进度条
            float left   = mIvIcon.getLeft();
            float top    = mIvIcon.getTop();
            float right  = mIvIcon.getRight();
            float bottom = mIvIcon.getBottom();
            if (oval == null)
            {
                oval = new RectF(left + 2, top + 2, right - 2, bottom - 2);
            }

            float startAngle = -90;//开始的角度
            float sweepAngle = 0;//扫过的角度;
            if (mMax == 0)
            {
                sweepAngle = mProgress * 360f / 100;
            }
            else
            {
                sweepAngle = mProgress * 360f / mMax;
            }

            boolean useCenter = false;//设置为false 则画出的扇形区域 不画中间部分 只画边缘弧度部分
            paint.setAntiAlias(true);
            //paint.setARGB(0xff, 30, 148, 254);
            paint.setColor(mARGB);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(UIUtils.dip2px(3));

            canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
        }

        super.onDraw(canvas);
    }
}
