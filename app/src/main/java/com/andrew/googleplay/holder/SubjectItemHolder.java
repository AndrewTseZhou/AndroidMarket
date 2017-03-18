package com.andrew.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrew.googleplay.R;
import com.andrew.googleplay.bean.SubjectBean;
import com.andrew.googleplay.utils.BitmapHelper;
import com.andrew.googleplay.utils.Constans;
import com.andrew.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.holder
 * @创建者: 谢康
 * @创建时间: 2016/11/17 下午 14:48
 * @描述: TODO
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class SubjectItemHolder extends BaseHolder<SubjectBean> {
    @ViewInject(R.id.item_subject_iv_icon)
    private ImageView mIvIcon;
    @ViewInject(R.id.item_subject_tv_title)
    private TextView  mTvTitle;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_subject, null);

        // 注入
        ViewUtils.inject(this, view);

        return view;
    }

    @Override
    protected void refreshUI(SubjectBean data) {
        mTvTitle.setText(data.des);
        mIvIcon.setImageResource(R.mipmap.ic_default);// 设置默认值

        // 需要去网络获取
        BitmapHelper.display(mIvIcon,  Constans.IMAGE_BASE_URL + data.url);
    }
}
