package com.andrew.googleplay.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.andrew.googleplay.R;
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
public class DrawerAboutFragment extends DrawerBaseFragment{
    private PagerSlidingTabStrip  mTabStrip;
    private ViewPager             mViewPager;
    private String[]              mMainTitles;

    public DrawerAboutFragment() {
    }

    public DrawerAboutFragment(Context context) {
        mActivity = (Activity) context;
    }

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.drawer_fragment_about, null);

        return view;
    }
}
