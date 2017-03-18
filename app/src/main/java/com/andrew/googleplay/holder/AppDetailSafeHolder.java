package com.andrew.googleplay.holder;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andrew.googleplay.R;
import com.andrew.googleplay.bean.AppInfoBean;
import com.andrew.googleplay.utils.BitmapHelper;
import com.andrew.googleplay.utils.Constans;
import com.andrew.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.holder
 * @创建者: 谢康
 * @创建时间: 2016/11/26 下午 15:31
 * @描述: 应用详情页面的安全信息的holder
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class AppDetailSafeHolder extends BaseHolder<List<AppInfoBean.AppSafeBean>>
        implements View.OnClickListener {
    @ViewInject(R.id.app_detail_safe_iv_arrow)
    private ImageView    mIvArrow;
    @ViewInject(R.id.app_detail_safe_pic_container)
    private LinearLayout mPicContainer;
    @ViewInject(R.id.app_detail_safe_des_container)
    private LinearLayout mDesContainer;
    private boolean isOpened = true;//默认安全信息是打开的

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_safe, null);

        ViewUtils.inject(this, view);

        view.setOnClickListener(this);

        return view;
    }

    @Override
    protected void refreshUI(List<AppInfoBean.AppSafeBean> data) {
        //清空mPicContainer和mDesContainer
        mPicContainer.removeAllViews();
        mDesContainer.removeAllViews();

        //动态添加数据
        for (int i = 0; i < data.size(); i++)
        {
            //获取数据
            AppInfoBean.AppSafeBean bean = data.get(i);

            //给图片容器添加view
            fillPicContainer(bean);
            //给安全信息描述容器添加view
            fillDesContainer(bean);
        }

        //详情页面的安全信息部分默认是打开的
        //所以打开详情页面的时候需要关闭
        toggle(false);
    }

    private void fillPicContainer(AppInfoBean.AppSafeBean bean) {
        //加载图片数据
        ImageView iv = new ImageView(UIUtils.getContext());
        BitmapHelper.display(iv, Constans.IMAGE_BASE_URL + bean.safeUrl);

        mPicContainer.addView(iv);
    }

    private void fillDesContainer(AppInfoBean.AppSafeBean bean) {
        LinearLayout layout = new LinearLayout(UIUtils.getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.setPadding(UIUtils.dip2px(8),
                          UIUtils.dip2px(4),
                          UIUtils.dip2px(8),
                          UIUtils.dip2px(4));

        //加载图片数据
        ImageView iv = new ImageView(UIUtils.getContext());
        BitmapHelper.display(iv, Constans.IMAGE_BASE_URL + bean.safeDesUrl);

        //加载文本数据
        TextView tv = new TextView(UIUtils.getContext());
        tv.setText(bean.safeDes);
        if (bean.safeDesColor == 0)
        {
            //文本设置为正常颜色
            tv.setTextColor(UIUtils.getColor(R.color.app_detail_safe_normal));
        }
        else
        {
            //文本设置为警告颜色
            tv.setTextColor(UIUtils.getColor(R.color.app_detail_safe_warning));
        }

        layout.addView(iv);
        layout.addView(tv);

        mDesContainer.addView(layout);
    }

    /**
     * 点击事件
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
     * 打开或关闭
     */
    private void toggle(boolean animated) {
        mDesContainer.measure(0, 0);
        int height = mDesContainer.getMeasuredHeight();

        if (isOpened)
        {
            //如果是打开的 就关闭 箭头由上到下
            //height-->0
            if (animated)
            {
                //需要做动画
                //int start = height;
                //int end   = 0;
                doAnimation(height, 0);
            }
            else
            {
                //不需要做动画
                ViewGroup.LayoutParams params = mDesContainer.getLayoutParams();
                params.height = 0;
                mDesContainer.setLayoutParams(params);
            }

            //箭头做动画
            ObjectAnimator.ofFloat(mIvArrow, "rotation", -180, 0).start();
        }
        else
        {
            //如果是关闭的 就打开 箭头由下到上
            //0-->height
            if (animated)
            {
                //需要做动画
                //int start = 0;
                //int end   = height;
                doAnimation(0, height);
            }
            else
            {
                //不需要做动画
                ViewGroup.LayoutParams params = mDesContainer.getLayoutParams();
                params.height = height;
                mDesContainer.setLayoutParams(params);
            }

            //箭头做动画
            ObjectAnimator.ofFloat(mIvArrow, "rotation", 0, -180).start();
        }
        isOpened = !isOpened;
    }

    private void doAnimation(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int                    value  = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = mDesContainer.getLayoutParams();
                params.height = value;
                mDesContainer.setLayoutParams(params);
            }
        });
        animator.start();
    }
}
