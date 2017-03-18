package com.andrew.googleplay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.andrew.googleplay.utils.UIUtils;

import java.util.List;
import java.util.Map;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.fragment
 * @创建者: 谢康
 * @创建时间: 2016/10/13 上午 11:11
 * @描述: TODO
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public abstract class TabStripBaseFragment extends Fragment {

    private LoadingPager mLoadingPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 视图:
        // 共同点：
        // 加载中，空页面，错误界面

        // 不同点：
        // 成功后的界面

        // 行为：
        // 同时只能显示一个页面
        // 加载中--->
        //      |-->空
        //      |-->错误
        //      |-->成功

        // 将共同点抽出来
        // View 需要包含 加载中，空页面，错误界面，成功，并且控制他们是否显示

        if (mLoadingPager == null) {
            mLoadingPager = new LoadingPager(UIUtils.getContext()) {

                @Override
                protected View initSuccessView() {
                    return onLoadSuccessView();
                }

                @Override
                protected LoadResult onLoadData() {
                    return onLoadingData();
                }
            };
        } else {
            ViewParent parent = mLoadingPager.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(mLoadingPager);
            }
        }

        return mLoadingPager;
    }

    public void loadData() {
        if (mLoadingPager != null) {
            mLoadingPager.loadData();
        }
    }

    protected LoadingPager.LoadResult checkData(Object data) {
        if (data == null) {
            return LoadingPager.LoadResult.EMPTY;
        }

        if (data instanceof List) {
            if (((List) data).size() == 0) {
                return LoadingPager.LoadResult.EMPTY;
            }
        }

        if (data instanceof Map) {
            if (((Map) data).size() == 0) {
                return LoadingPager.LoadResult.EMPTY;
            }
        }

        return LoadingPager.LoadResult.SUCCESS;
    }

    protected abstract View onLoadSuccessView();

    protected abstract LoadingPager.LoadResult onLoadingData();
}
