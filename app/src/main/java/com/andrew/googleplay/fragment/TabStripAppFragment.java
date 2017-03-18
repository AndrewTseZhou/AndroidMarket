package com.andrew.googleplay.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.andrew.googleplay.adapter.AppListAdapter;
import com.andrew.googleplay.bean.AppInfoBean;
import com.andrew.googleplay.http.AppProtocol;
import com.andrew.googleplay.utils.ListViewFactory;

import java.util.List;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.fragment
 * @创建者: 谢康
 * @创建时间: 2016/10/22 上午11:34
 * @描述: 主页中fragment的基类
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class TabStripAppFragment extends TabStripBaseFragment {
    private List<AppInfoBean> mDatas;
    private AppProtocol       mProtocol;

    @Override
    protected View onLoadSuccessView() {
        ListView listView = ListViewFactory.getListView();

        // 设置数据 -->adapter ---> list
        listView.setAdapter(new AppAdapter(listView, mDatas));

        return listView;
    }

    @Override
    protected LoadingPager.LoadResult onLoadingData() {
        mProtocol = new AppProtocol();

        try {
            mDatas = mProtocol.loadData(0);
            return checkData(mDatas);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }
    }

    class AppAdapter extends AppListAdapter {

        public AppAdapter(AbsListView listView, List<AppInfoBean> datas) {
            super(listView, datas);
        }

        @Override
        protected List<AppInfoBean> onLoadMoreData() throws Exception {
            return mProtocol.loadData(mDatas.size());
        }
    }
}
