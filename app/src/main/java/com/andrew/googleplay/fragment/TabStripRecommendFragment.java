package com.andrew.googleplay.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.andrew.googleplay.http.RecommendProtocol;
import com.andrew.googleplay.utils.UIUtils;
import com.andrew.googleplay.widget.ShakeListener;
import com.andrew.googleplay.widget.StellarMap;

import java.util.List;
import java.util.Random;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.fragment
 * @创建者: 谢康
 * @创建时间: 2016/11/22 下午 12:36
 * @描述: 推荐页面
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class TabStripRecommendFragment extends TabStripBaseFragment {
    private List<String>      mDatas;
    private RecommendProtocol mProtocol;
    private StellarMap        mView;
    private ShakeListener     mShake;
    private RecommendAdapter  mAdapter;

    @Override
    protected View onLoadSuccessView() {
        mView = new StellarMap(UIUtils.getContext());
        //设置数据
        mAdapter = new RecommendAdapter();
        mView.setAdapter(mAdapter);
        //设置随机摆放的区域
        mView.setRegularity(15, 15);
        //设置默认选中页
        mView.setGroup(0, true);
        //设置padding
        mView.setInnerPadding(UIUtils.dip2px(10),
                              UIUtils.dip2px(10),
                              UIUtils.dip2px(10),
                              UIUtils.dip2px(10));
        //设置手机摇一摇功能
        mShake = new ShakeListener(UIUtils.getContext());
        mShake.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                int currentGroup = mView.getCurrentGroup();
                if (currentGroup == mAdapter.getGroupCount() - 1) {
                    currentGroup = 0;
                } else {
                    currentGroup++;
                }
                mView.setGroup(currentGroup, true);
            }
        });

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mShake != null) {
            mShake.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mShake != null) {
            mShake.pause();
        }
    }

    @Override
    protected LoadingPager.LoadResult onLoadingData() {
        mProtocol = new RecommendProtocol();
        try {
            mDatas = mProtocol.loadData(0);
            return checkData(mDatas);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }
    }

    private class RecommendAdapter implements StellarMap.Adapter {
        private final static int PER_PAGE_SIZE = 15;

        /**
         * 有几个group
         *
         * @return
         */
        @Override
        public int getGroupCount() {
            if (mDatas != null) {
                int size  = mDatas.size();
                int count = size / PER_PAGE_SIZE;
                if (size % count > 0) {
                    count++;
                }
                return count;
            }
            return 0;
        }

        /**
         * 第group页面有几条数据
         *
         * @param group
         * @return
         */
        @Override
        public int getCount(int group) {
            if (mDatas != null) {
                int size = mDatas.size();

                // 如果是最后一页
                if (group == (getGroupCount() - 1)) {
                    if (size % PER_PAGE_SIZE > 0) {
                        // 最后一页有多余,不够一页的数量
                        return size % PER_PAGE_SIZE;
                    } else {
                        return PER_PAGE_SIZE;
                    }
                }
                return PER_PAGE_SIZE;
            }
            return 0;
        }

        @Override
        public View getView(int group, int position, View convertView) {
            TextView tv = new TextView(UIUtils.getContext());
            tv.setText(mDatas.get(position));

            Random random = new Random();
            int    alpha  = 0xff;
            int    red    = random.nextInt(200) + 30;
            int    green  = random.nextInt(200) + 30;
            int    blue   = random.nextInt(200) + 30;
            int    argb   = Color.argb(alpha, red, green, blue);
            tv.setTextColor(argb);

            int size = UIUtils.dip2px(random.nextInt(10) + 15);
            tv.setTextSize(size);

            return tv;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return 0;
        }
    }
}
