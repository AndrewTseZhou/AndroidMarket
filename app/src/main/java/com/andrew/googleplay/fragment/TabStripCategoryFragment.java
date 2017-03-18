package com.andrew.googleplay.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.andrew.googleplay.adapter.SuperBaseAdapter;
import com.andrew.googleplay.bean.CategoryBean;
import com.andrew.googleplay.holder.BaseHolder;
import com.andrew.googleplay.holder.CategoryItemHolder;
import com.andrew.googleplay.holder.CategoryTitleHolder;
import com.andrew.googleplay.http.CategoryProtocol;
import com.andrew.googleplay.utils.ListViewFactory;

import java.util.List;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.fragment
 * @创建者: 谢康
 * @创建时间: 2016/11/20 下午 19:53
 * @描述: 分类页面
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class TabStripCategoryFragment extends TabStripBaseFragment {
    private List<CategoryBean> mDatas;
    private CategoryProtocol   mProtocol;

    @Override
    protected View onLoadSuccessView() {
        ListView listView = ListViewFactory.getListView();
        listView.setAdapter(new CategoryAdapter(listView, mDatas));

        return listView;
    }

    @Override
    protected LoadingPager.LoadResult onLoadingData() {
        mProtocol = new CategoryProtocol();

        try {
            mDatas = mProtocol.loadData(0);
            return checkData(mDatas);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }
    }

    private class CategoryAdapter extends SuperBaseAdapter<CategoryBean> {

        public CategoryAdapter(AbsListView listView, List<CategoryBean> datas) {
            super(listView, datas);
        }

        @Override
        protected BaseHolder<CategoryBean> getItemHolder(int position) {
            CategoryBean bean = mDatas.get(position);
            if (bean.isTitle) {
                //显示title的holder
                return new CategoryTitleHolder();
            } else {
                //显示item的holder
                return new CategoryItemHolder();
            }
        }

        @Override
        protected boolean hasLoadMore() {
            return false;
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount() + 1;
        }

        @Override
        protected int getNormalItemViewType(int position) {
            CategoryBean bean = mDatas.get(position);
            if (bean.isTitle) {
                return super.getNormalItemViewType(position);
            } else {
                return super.getNormalItemViewType(position) + 1;
            }
        }
    }
}
