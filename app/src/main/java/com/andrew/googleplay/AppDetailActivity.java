package com.andrew.googleplay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.andrew.googleplay.bean.AppInfoBean;
import com.andrew.googleplay.fragment.LoadingPager;
import com.andrew.googleplay.holder.AppDetailBottomHolder;
import com.andrew.googleplay.holder.AppDetailDesHolder;
import com.andrew.googleplay.holder.AppDetailInfoHolder;
import com.andrew.googleplay.holder.AppDetailPicHolder;
import com.andrew.googleplay.holder.AppDetailSafeHolder;
import com.andrew.googleplay.http.AppDetailProtocol;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay
 * @创建者: 谢康
 * @创建时间: 2016/11/22 下午 14:27
 * @描述: 应用详情页面
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class AppDetailActivity extends AppCompatActivity {
    public static final String KEY_PACKAGENAME = "packageName";
    private LoadingPager          mLoadingPager;
    private AppDetailProtocol     mProtocol;
    private AppInfoBean           mDatas;
    @ViewInject(R.id.app_detail_bar)
    private Toolbar               mToolbar;
    @ViewInject(R.id.app_detail_container_info)
    private FrameLayout           mContainerInfo;
    @ViewInject(R.id.app_detail_container_safe)
    private FrameLayout           mContainerSafe;
    @ViewInject(R.id.app_detail_container_pic)
    private FrameLayout           mContainerPic;
    @ViewInject(R.id.app_detail_container_des)
    private FrameLayout           mContainerDes;
    @ViewInject(R.id.app_detail_container_bottom)
    private FrameLayout           mContainerBottom;
    private AppDetailBottomHolder mBottomHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    protected void initView() {
        mLoadingPager = new LoadingPager(this) {
            @Override
            protected View initSuccessView() {
                return onSuccessView();
            }

            @Override
            protected LoadResult onLoadData() {
                return performLoadingData();
            }
        };

        //加载视图
        setContentView(mLoadingPager);
    }

    private View onSuccessView() {
        View view = View.inflate(this, R.layout.activity_app_detail, null);
        ViewUtils.inject(this, view);
        initToolbar();

        //加载对应的holder数据
        //1.应用信息的holder
        AppDetailInfoHolder infoHolder = new AppDetailInfoHolder();
        mContainerInfo.addView(infoHolder.getRootView());
        infoHolder.setData(mDatas);

        //2.安全信息的holder
        AppDetailSafeHolder safeHolder = new AppDetailSafeHolder();
        mContainerSafe.addView(safeHolder.getRootView());
        safeHolder.setData(mDatas.safe);

        //3.屏幕截图的holder
        AppDetailPicHolder picHolder = new AppDetailPicHolder();
        mContainerPic.addView(picHolder.getRootView());
        picHolder.setData(mDatas.screen);

        //4.应用简介的holder
        AppDetailDesHolder desHolder = new AppDetailDesHolder();
        mContainerDes.addView(desHolder.getRootView());
        desHolder.setData(mDatas);

        //5.底部的holder
        mBottomHolder = new AppDetailBottomHolder();
        mContainerBottom.addView(mBottomHolder.getRootView());
        mBottomHolder.setData(mDatas);

        //通过activity去注册监听下载
        mBottomHolder.startObserver();

        return view;
    }

    protected void initToolbar() {
        //设置Toolbar
//        System.out.println(mToolbar);
        mToolbar.setTitle("应用详情");//设置title部分
        mToolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(mToolbar);

        //设置返回按钮可见
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置返回按钮可用
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //onBackPressed();
            }
        });
    }

    protected void initData() {
        //加载数据
        mLoadingPager.loadData();
    }

    /**
     * 加载数据
     *
     * @return
     */
    private LoadingPager.LoadResult performLoadingData() {
        String packageName = getIntent().getStringExtra(KEY_PACKAGENAME);
        mProtocol = new AppDetailProtocol(packageName);

        try
        {
            mDatas = mProtocol.loadData(0);

            if (mDatas == null)
            {
                return LoadingPager.LoadResult.EMPTY;
            }
            return LoadingPager.LoadResult.SUCCESS;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBottomHolder != null)
        {
            mBottomHolder.startObserver();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBottomHolder != null)
        {
            mBottomHolder.stopObserver();
        }
    }

    /**
     * actionButton被点击时调用
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
