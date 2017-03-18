package com.andrew.googleplay.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.andrew.googleplay.R;
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
 * @创建时间: 2016/11/17 下午 12:16
 * @描述: 轮播图的holder
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class HomePictureHolder extends BaseHolder<List<String>>
        implements ViewPager.OnPageChangeListener {
    @ViewInject(R.id.item_home_picture_pager)
    private ViewPager      mViewPager;
    @ViewInject(R.id.item_home_picture_container_indicator)
    private LinearLayout   mPointContainer;
    private List<String>   mPictures;
    private AutoSwitchTask mAutoSwitchTask;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_home_picture, null);

        // 注入
        ViewUtils.inject(this, view);

        return view;
    }

    @Override
    protected void refreshUI(List<String> data) {
        this.mPictures = data;
        mViewPager.setAdapter(new HomePictureAdapter());
        //添加指示点
        mPointContainer.removeAllViews();
        for (int i = 0; i < data.size(); i++) {
            View view = new View(UIUtils.getContext());
            view.setBackgroundResource(R.drawable.indicator_normal);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2px(6),
                                                                             UIUtils.dip2px(6));
            if (i != 0) {
                params.leftMargin = UIUtils.dip2px(8);
                params.bottomMargin = UIUtils.dip2px(8);
            } else {
                view.setBackgroundResource(R.drawable.indicator_selected);//设置默认选中
            }

            mPointContainer.addView(view, params);
        }

        //设置viewPager的监听
        mViewPager.addOnPageChangeListener(this);

        //为了让ViewPager轮播图能左右无限滚动
        //给viewPager设置中间选中的值
        int middle = Integer.MAX_VALUE / 2;
        int extra  = middle % mPictures.size();//如果没有extra 则轮播图默认选中最后一个
        mViewPager.setCurrentItem(middle - extra);

        //开始轮播任务
        if (mAutoSwitchTask == null) {
            mAutoSwitchTask = new AutoSwitchTask();
        }
        mAutoSwitchTask.start();

        //设置ViewPager的touch监听
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mAutoSwitchTask.stop();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mAutoSwitchTask.start();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        position = position % mPictures.size();

        for (int i = 0; i < mPointContainer.getChildCount(); i++) {
            View view = mPointContainer.getChildAt(i);
            view.setBackgroundResource(i == position
                                       ? R.drawable.indicator_selected
                                       : R.drawable.indicator_normal);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class HomePictureAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            if (mPictures != null) {
                return Integer.MAX_VALUE;//返回最大值为了让轮播图可以无限轮播
//                return mPictures.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % mPictures.size();

            ImageView imageView = new ImageView(UIUtils.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //给imageView设置数据
            imageView.setImageResource(R.mipmap.ic_default);
            //设置网络图片
            BitmapHelper.display(imageView, Constans.IMAGE_BASE_URL + mPictures.get(position));

            //给viewPager加视图
            container.addView(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 轮播图自动滚动
     */
    class AutoSwitchTask implements Runnable {

        //开始轮播
        public void start() {
            stop();
            UIUtils.postDelayed(this, 2000);
        }

        //停止轮播
        public void stop() {
            UIUtils.removeCallbacks(this);
        }

        //让viewPager自动选中下一个
        @Override
        public void run() {
            int currentItem = mViewPager.getCurrentItem();
            mViewPager.setCurrentItem(++currentItem);
            UIUtils.postDelayed(this, 2000);
        }
    }
}
