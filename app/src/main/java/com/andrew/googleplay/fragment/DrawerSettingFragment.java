package com.andrew.googleplay.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.andrew.googleplay.R;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.fragment
 * @创建者: 谢康
 * @创建时间: 2016/12/21 下午 22:05
 * @描述: TODO
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
@SuppressLint("ValidFragment")
public class DrawerSettingFragment extends DrawerBaseFragment {
    private TextView textView;

    public DrawerSettingFragment() {
    }

    public DrawerSettingFragment(Context context) {
        mActivity = (Activity) context;
    }

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.drawer_fragment_setting, null);
        textView= (TextView) view.findViewById(R.id.setting_fragment);

        return view;
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
