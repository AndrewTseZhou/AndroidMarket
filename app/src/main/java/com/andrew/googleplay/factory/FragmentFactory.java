package com.andrew.googleplay.factory;

import android.support.v4.util.SparseArrayCompat;

import com.andrew.googleplay.fragment.TabStripAppFragment;
import com.andrew.googleplay.fragment.TabStripBaseFragment;
import com.andrew.googleplay.fragment.TabStripCategoryFragment;
import com.andrew.googleplay.fragment.TabStripGameFragment;
import com.andrew.googleplay.fragment.TabStripHomeFragment;
import com.andrew.googleplay.fragment.TabStripHotFragment;
import com.andrew.googleplay.fragment.TabStripRecommendFragment;
import com.andrew.googleplay.fragment.TabStripSubjectFragment;
import com.andrew.googleplay.utils.LogUtils;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.factory
 * @创建者: 谢康
 * @创建时间: 2016/10/12 下午 21:15
 * @描述: 获得模块的工厂
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class FragmentFactory {
    //private static Map<Integer, Fragment> mCaches = new HashMap<>();

    private static SparseArrayCompat<TabStripBaseFragment> mCaches = new SparseArrayCompat<>();

    public static TabStripBaseFragment getFragment(int position) {
        //去缓存中取
        TabStripBaseFragment fragment = mCaches.get(position);
        if (fragment != null) {
            LogUtils.d("使用" + position + "的缓存");
            //缓存中有 直接返回fragment
            return fragment;
        }

        switch (position) {
            case 0:
                //首页
                fragment = new TabStripHomeFragment();
                break;
            case 1:
                //应用
                fragment = new TabStripAppFragment();
                break;
            case 2:
                //游戏
                fragment = new TabStripGameFragment();
                break;
            case 3:
                //专题
                fragment = new TabStripSubjectFragment();
                break;
            case 4:
                //推荐
                fragment = new TabStripRecommendFragment();
                break;
            case 5:
                //分类
                fragment = new TabStripCategoryFragment();
                break;
            case 6:
                //排行
                fragment = new TabStripHotFragment();
                break;
        }

        //将fragment缓存起来
        LogUtils.d("缓存第" + position + "个页面");
        mCaches.put(position, fragment);

        return fragment;
    }
}
