package com.andrew.googleplay.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.andrew.googleplay.adapter.AppListAdapter;
import com.andrew.googleplay.bean.AppInfoBean;
import com.andrew.googleplay.bean.HomeBean;
import com.andrew.googleplay.holder.HomePictureHolder;
import com.andrew.googleplay.http.HomeProtocol;
import com.andrew.googleplay.utils.ListViewFactory;

import java.util.List;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.fragment
 * @创建者: 谢康
 * @创建时间: 2016/10/12 下午 21:20
 * @描述: 主页中fragment的基类
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class TabStripHomeFragment extends TabStripBaseFragment {
    private List<AppInfoBean> mDatas;//listView对应的数据
    private List<String>      mPictures;//轮播图对应的数据
    private HomeProtocol      mProtocol;
    private HomeAdapter       mAdapter;

    /**
     * 当访问网络成功后
     * 加载视图
     *
     * @return
     */
    @Override
    protected View onLoadSuccessView() {
        ListView listView = ListViewFactory.getListView();

        //创建轮播图的holder
        HomePictureHolder homePictureHolder = new HomePictureHolder();
        //给listView添加头
        listView.addHeaderView(homePictureHolder.getRootView());
        //给holder设置数据
        homePictureHolder.setData(mPictures);

        mAdapter = new HomeAdapter(listView, mDatas);
        listView.setAdapter(mAdapter);
        mAdapter.startObserver();

        return listView;
    }

    /**
     * 加载数据
     *
     * @return
     */
    @Override
    protected LoadingPager.LoadResult onLoadingData() {
        mProtocol = new HomeProtocol();

        try {
            HomeBean bean = mProtocol.loadData(0);

            // 判断bean是否为空
            LoadingPager.LoadResult result = checkData(bean);
            if (result != LoadingPager.LoadResult.SUCCESS) {
                return result;
            }

            result = checkData(bean.list);
            if (result != LoadingPager.LoadResult.SUCCESS) {
                return result;
            }

            mDatas = bean.list;
            mPictures = bean.picture;

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mAdapter!=null)
        {
            mAdapter.startObserver();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mAdapter!=null)
        {
            mAdapter.stopObserver();
        }
    }

    /**
     * listView的Adapter
     */
    class HomeAdapter extends AppListAdapter {

        public HomeAdapter(AbsListView listView, List<AppInfoBean> datas) {
            super(listView, datas);
        }

        @Override
        protected List<AppInfoBean> onLoadMoreData() throws Exception {
            return loadMoreData(mDatas.size());
        }
    }

    /**
     * 加载更多数据
     *
     * @param index
     * @return
     * @throws Exception
     */
    private List<AppInfoBean> loadMoreData(int index) throws Exception {
        HomeBean bean = mProtocol.loadData(index);
        if (bean == null) {
            return null;
        }
        return bean.list;
    }
}
