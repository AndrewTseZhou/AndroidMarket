package com.andrew.googleplay.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.andrew.googleplay.R;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.widget
 * @创建者: 谢康
 * @创建时间: 2016/11/18 下午 14:36
 * @描述: 设置子类孩子的大小
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class RatioLayout extends FrameLayout {
    public static final int RELATIVE_WIDTH  = 0;
    public static final int RELATIVE_HEIGHT = 1;
    private float mRatio;//可以直接设置 也可以通过自定义view属性设置
    private int mRelative = RELATIVE_WIDTH;

    public RatioLayout(Context context) {
        this(context, null);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        //自定义view属性
        //在attr.xml中
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        mRatio = typedArray.getFloat(R.styleable.RatioLayout_ratio, 0);
        mRelative = typedArray.getInt(R.styleable.RatioLayout_relative, RELATIVE_WIDTH);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //通过宽高比和宽度值计算高度
        //android:layout_width="match_parent"
        //android:layout_height="wrap_content"
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        //通过宽高比和高度计算宽度
        //android:layout_width="wrap_content"
        //android:layout_height="match_parent"
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY && mRatio != 0 && mRelative == RELATIVE_WIDTH)
        {
            int width  = widthSize - getPaddingLeft() - getPaddingRight();
            int height = (int) (width / mRatio + 0.5f);

            //给孩子设置宽高
            int childWidthMeasureSpec  = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

            //给自己设置宽度和高度
            //在调用onMeasure()方法后 必须调用setMeasuredDimension();
            setMeasuredDimension(widthSize, height + getPaddingTop() + getPaddingBottom());
        }
        else if (heightMode == MeasureSpec.EXACTLY && mRatio != 0 && mRelative == RELATIVE_HEIGHT)
        {
            int height = heightSize - getPaddingTop() - getPaddingBottom();
            int width  = (int) (height * mRatio + 0.5f);

            //给孩子设置宽高
            int childWidthMeasureSpec  = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

            //给自己设置宽度和高度
            setMeasuredDimension(width + getPaddingLeft() + getPaddingRight(), heightSize);
        }
        else
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setRatio(float ratio) {
        this.mRatio = ratio;
    }

    public void setRelative(int relative) {
        this.mRelative = relative;
    }
}
