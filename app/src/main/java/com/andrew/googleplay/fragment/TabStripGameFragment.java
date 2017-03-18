package com.andrew.googleplay.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.andrew.googleplay.adapter.AppListAdapter;
import com.andrew.googleplay.bean.AppInfoBean;
import com.andrew.googleplay.http.GameProtocol;
import com.andrew.googleplay.utils.ListViewFactory;

import java.util.List;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.fragment
 * @创建者: 谢康
 * @创建时间: 2016/11/9 下午 13:51
 * @描述: TODO
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class TabStripGameFragment extends TabStripBaseFragment {
    private GameProtocol      mProtocol;
    private List<AppInfoBean> mDatas;

    @Override
    protected View onLoadSuccessView() {
        ListView listView = ListViewFactory.getListView();

        //给listView设置数据
        listView.setAdapter(new GameAdapter(listView, mDatas));

        return listView;
    }

    @Override
    protected LoadingPager.LoadResult onLoadingData() {
        mProtocol = new GameProtocol();

        try {
            mDatas = mProtocol.loadData(0);
            return checkData(mDatas);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }
    }

    class GameAdapter extends AppListAdapter {

        public GameAdapter(AbsListView listView, List<AppInfoBean> datas) {
            super(listView, datas);
        }

        @Override
        protected List<AppInfoBean> onLoadMoreData() throws Exception {
            return mProtocol.loadData(mDatas.size());
        }
    }
}
