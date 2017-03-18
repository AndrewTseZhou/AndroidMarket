package com.andrew.googleplay.holder;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.andrew.googleplay.R;
import com.andrew.googleplay.bean.AppInfoBean;
import com.andrew.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.holder
 * @创建者: 谢康
 * @创建时间: 2016/11/27 上午 10:32
 * @描述: TODO
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class AppDetailDesHolder extends BaseHolder<AppInfoBean> implements View.OnClickListener {
    @ViewInject(R.id.app_detail_des_tv_des)
    private TextView  mTvDes;
    @ViewInject(R.id.app_detail_des_tv_author)
    private TextView  mTvAuthor;
    @ViewInject(R.id.app_detail_des_iv_arrow)
    private ImageView mIvArrow;
    private int       mDesHeight;
    private int       mDesWidth;
    private boolean isOpened = true;//默认简介部分的内容是全部打开的

    @Override
    protected View initView() {
        final View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_des, null);

        ViewUtils.inject(this, view);

        view.setOnClickListener(this);

        view.getViewTreeObserver()
            .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mDesWidth = mTvDes.getMeasuredWidth();
                    mDesHeight = mTvDes.getMeasuredHeight();

                    //简介部分的内容默认是打开的
                    //所以打开详情页面的时候需要关闭 减少显示的内容
                    toggle(false);

                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });

        return view;
    }

    @Override
    protected void refreshUI(AppInfoBean data) {
        mTvDes.setText(data.des);
        String author = UIUtils.getString(R.string.app_detail_des_author, data.author);
        mTvAuthor.setText(author);
    }

    /**
     * 应用简介部分的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == getRootView())
        {
            toggle(true);
        }
    }

    /**
     * 用于打开或关闭简介的内容部分
     *
     * @param animated
     */
    private void toggle(boolean animated) {
        int shortHeight = getShortHeight(mData.des);

        if (isOpened)
        {
            //如果是打开的 就关闭 箭头由上到下
            int start = 0;
            int end   = 0;

            if (shortHeight > mDesHeight)
            {
                start = shortHeight;
                end = mDesHeight;
            }
            else
            {
                start = mDesHeight;
                end = shortHeight;
            }

            if (animated)
            {
                //需要做动画
                doAnimation(start, end);
            }
            else
            {
                //不需要做动画
                ViewGroup.LayoutParams params = mTvDes.getLayoutParams();
                params.height = end;
                mTvDes.setLayoutParams(params);
            }
            //箭头做动画
            ObjectAnimator.ofFloat(mIvArrow, "rotation", -180, 0).start();
        }
        else
        {
            //如果是关闭的 就打开 箭头由下到上
            int start = 0;
            int end   = 0;

            if (shortHeight < mDesHeight)
            {
                start = shortHeight;
                end = mDesHeight;
            }
            else
            {
                start = mDesHeight;
                end = shortHeight;
            }
            if (animated)
            {
                //需要做动画
                doAnimation(start, end);
            }
            else
            {
                //不需要做动画
                ViewGroup.LayoutParams params = mTvDes.getLayoutParams();
                params.height = end;
                mTvDes.setLayoutParams(params);
            }
            //箭头做动画
            ObjectAnimator.ofFloat(mIvArrow, "rotation", 0, -180).start();
        }
        isOpened = !isOpened;
    }

    /**
     * 内容界面展开、关闭动画
     *
     * @param start
     * @param end
     */
    private void doAnimation(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int                    value  = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = mTvDes.getLayoutParams();
                params.height = value;
                mTvDes.setLayoutParams(params);
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //当动画结束的时候 scrollView需要滚动到底部
                //scrollView需要从parent中获得
                View       rootView   = getRootView();
                ViewParent parent     = rootView.getParent();
                ScrollView scrollView = null;
                if (parent != null && parent instanceof ViewGroup)
                {
                    while (true)
                    {
                        parent = parent.getParent();
                        if (parent!=null&&parent instanceof ScrollView)
                        {
                            scrollView= (ScrollView) parent;
                            break;
                        }
                        if (parent==null)
                        {
                            break;
                        }
                    }

                    //让scrollView自动滚动到底部
                    scrollView.fullScroll(View.FOCUS_DOWN);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.start();
    }

    /**
     * 当简介内容不完全展开时调用
     * 用于获得不完全展开时的高度
     *
     * @param des
     * @return
     */
    private int getShortHeight(String des) {
        TextView tv = new TextView(UIUtils.getContext());
        tv.setText(des);
        tv.setLines(7);//设置7行数据

        tv.measure(View.MeasureSpec.makeMeasureSpec(mDesWidth, View.MeasureSpec.EXACTLY), 0);
        return tv.getMeasuredHeight();
    }
}
