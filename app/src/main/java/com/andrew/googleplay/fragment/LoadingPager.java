package com.andrew.googleplay.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.andrew.googleplay.R;
import com.andrew.googleplay.manager.ThreadManager;
import com.andrew.googleplay.utils.UIUtils;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.fragment
 * @创建者: 谢康
 * @创建时间: 2016/10/13 上午 11:26
 * @描述: View 需要包含 加载中，空页面，错误界面，成功页面，并且控制他们是否显示
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public abstract class LoadingPager extends FrameLayout implements View.OnClickListener {
    private final static int STATE_NONE    = 0;//空状态
    private final static int STATE_LOADING = 1;//加载中的状态
    private final static int STATE_EMPTY   = 2;//空页面的状态
    private final static int STATE_ERROR   = 3;//错误页面的状态
    private final static int STATE_SUCCESS = 4;//成功页面的状态

    private View mLoadingView;
    private View mEmptyView;
    private View mErrorView;
    private View mSuccessView;
    private View mRetryView;

    private int mCurrentState = STATE_NONE;//默认是空的状态

    public LoadingPager(Context context) {
        super(context);

        initView();
    }

    public LoadingPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    private void initView() {
        // 加载 （加载中，空页面，错误界面，成功）4个页面

        //加载中页面
        if (mLoadingView == null) {
            mLoadingView = View.inflate(getContext(), R.layout.pager_loading, null);
            //添加到容器里
            addView(mLoadingView);
        }

        //空页面
        if (mEmptyView == null) {
            mEmptyView = View.inflate(getContext(), R.layout.pager_empty, null);
            //添加到容器里
            addView(mEmptyView);
        }

        //错误页面
        if (mErrorView == null) {
            mErrorView = View.inflate(getContext(), R.layout.pager_error, null);
            //添加到容器里
            addView(mErrorView);

            mRetryView = mErrorView.findViewById(R.id.error_btn_retry);
            mRetryView.setOnClickListener(this);
        }

        //成功页面 等数据加载成功后添加

        //通过状态更新UI的显示
        safeUpdateUI();
    }

    private void safeUpdateUI() {
        UIUtils.post(new Runnable() {
            @Override
            public void run() {
                updateUI();
            }
        });
    }

    private void updateUI() {
        mLoadingView.setVisibility((mCurrentState == STATE_NONE || mCurrentState == STATE_LOADING)
                                   ? VISIBLE
                                   : GONE);
        mEmptyView.setVisibility(mCurrentState == STATE_EMPTY ? VISIBLE : GONE);
        mErrorView.setVisibility(mCurrentState == STATE_ERROR ? VISIBLE : GONE);

        if (mCurrentState == STATE_SUCCESS && mSuccessView == null) {
            //创建成功页面的View
            mSuccessView = initSuccessView();
            //添加到容器中
            addView(mSuccessView);
        }

        if (mSuccessView != null) {
            mSuccessView.setVisibility(mCurrentState == STATE_SUCCESS ? VISIBLE : GONE);
        }
    }

    /**
     * 加载数据
     */
    public void loadData() {
        if (mCurrentState != STATE_SUCCESS && mCurrentState != STATE_LOADING) {
            mCurrentState = STATE_LOADING;
            safeUpdateUI();
            //new Thread(new LoadDataTask()).start();
            ThreadManager.getLongPool().excute(new LoadDataTask());
        }
    }

    /**
     * 让子类实现
     *
     * @return
     */
    protected abstract View initSuccessView();

    protected abstract LoadResult onLoadData();

    class LoadDataTask implements Runnable {
        @Override
        public void run() {
            //去加载数据
            LoadResult result = onLoadData();
            mCurrentState = result.getState();

            safeUpdateUI();
        }
    }

    public enum LoadResult {
        EMPTY(STATE_EMPTY), ERROR(STATE_ERROR), SUCCESS(STATE_SUCCESS);

        private int state;

        private LoadResult(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mRetryView) {
            loadData();
        }
    }
}
