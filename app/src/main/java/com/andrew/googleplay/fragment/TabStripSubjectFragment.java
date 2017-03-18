package com.andrew.googleplay.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.andrew.googleplay.adapter.SuperBaseAdapter;
import com.andrew.googleplay.bean.SubjectBean;
import com.andrew.googleplay.holder.BaseHolder;
import com.andrew.googleplay.holder.SubjectItemHolder;
import com.andrew.googleplay.http.SubjectProtocol;
import com.andrew.googleplay.utils.ListViewFactory;

import java.util.List;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.fragment
 * @创建者: 谢康
 * @创建时间: 2016/11/17 下午 14:19
 * @描述: 专题页面
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class TabStripSubjectFragment extends TabStripBaseFragment {
    private List<SubjectBean> mDatas;
    private SubjectProtocol   mProtocol;

    @Override
    protected View onLoadSuccessView() {
        ListView listView = ListViewFactory.getListView();

        listView.setAdapter(new SubjectAdapter(listView, mDatas));

        return listView;
    }

    @Override
    protected LoadingPager.LoadResult onLoadingData() {
        mProtocol = new SubjectProtocol();

        try {
            mDatas = mProtocol.loadData(0);
            return checkData(mDatas);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }
    }

    private class SubjectAdapter extends SuperBaseAdapter<SubjectBean> {

        public SubjectAdapter(AbsListView listView, List<SubjectBean> datas) {
            super(listView, datas);
        }

        @Override
        protected BaseHolder<SubjectBean> getItemHolder(int position) {
            return new SubjectItemHolder();
        }

        @Override
        protected List<SubjectBean> onLoadMoreData() throws Exception {
            return mProtocol.loadData(mDatas.size());
        }
    }
}
