package com.andrew.googleplay.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.andrew.googleplay.R;
import com.andrew.googleplay.factory.FragmentFactory;
import com.andrew.googleplay.utils.UIUtils;
import com.astuetz.PagerSlidingTabStrip;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.fragment
 * @创建者: 谢康
 * @创建时间: 2016/12/21 下午 22:32
 * @描述: TODO
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
@SuppressLint("ValidFragment")
public class DrawerFeedbackFragment extends DrawerBaseFragment
        implements ViewPager.OnPageChangeListener {
    private PagerSlidingTabStrip  mTabStrip;
    private ViewPager             mViewPager;
    private String[]              mMainTitles;

    public DrawerFeedbackFragment() {
    }

    public DrawerFeedbackFragment(Context context) {
        mActivity = (Activity) context;
    }

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.drawer_fragment_home, null);
        mTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.main_tabs);
        mViewPager = (ViewPager) view.findViewById(R.id.main_pager);

        //设置指针文本的颜色
        //int normalColor   = getResources().getColor(R.color.tab_text_normal);
        //int selectedColor = getResources().getColor(tab_text_selected);
        int normalColor   = UIUtils.getColor(R.color.tab_text_normal);
        int selectedColor = UIUtils.getColor(R.color.tab_text_selected);
        mTabStrip.setTextColor(normalColor, selectedColor);

        return view;
    }

    @Override
    protected void initData() {
        //初始化title
        mMainTitles = UIUtils.getStringArray(R.array.main_titles);

        //给ViewPager设置Adapter
//        mViewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager()));


        //给tabstrip设置ViewPager
        mTabStrip.setViewPager(mViewPager);

        // 设置ViewPager的监听
        mTabStrip.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 当选中的时候去加载数据
        TabStripBaseFragment fragment = FragmentFactory.getFragment(position);
        fragment.loadData();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MainFragmentPagerAdapter extends FragmentPagerAdapter {

        public MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.getFragment(position);
        }

        @Override
        public int getCount() {
            if (mMainTitles != null)
            {
                return mMainTitles.length;
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mMainTitles != null)
            {
                return mMainTitles[position];
            }
            return super.getPageTitle(position);
        }
    }
}
