package com.andrew.googleplay.holder;

import android.view.View;

import com.andrew.googleplay.R;
import com.andrew.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.holder
 * @创建者: 谢康
 * @创建时间: 2016/10/19 上午 11:21
 * @描述: 加载更多的holder
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class LoadMoreHolder extends BaseHolder<Integer> {
    public final static int STATE_LOADING = 0;//正在加载中
    public final static int STATE_ERROR   = 1;//加载失败
    public final static int STATE_EMPTY   = 2;//没有加载更多的功能或没有内容可以加载

    @ViewInject(R.id.item_loadmore_container_loading)
    private View mLoadingView;
    @ViewInject(R.id.item_loadmore_container_retry)
    private View mErrorView;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_load_more, null);

        //ViewUtils注入
        ViewUtils.inject(this, view);

        return view;
    }

    @Override
    protected void refreshUI(Integer data) {
        // 根据数据显示View
        switch (data) {
            case STATE_LOADING:
                mLoadingView.setVisibility(View.VISIBLE);
                mErrorView.setVisibility(View.GONE);
                break;
            case STATE_ERROR:
                mLoadingView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.VISIBLE);
                break;
            case STATE_EMPTY:
                mLoadingView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 获取加载更多的状态
     *
     * @return
     */
    public int getCurrentState() {
        return mData;
    }
}
