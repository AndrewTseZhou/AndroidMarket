package com.andrew.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.andrew.googleplay.R;
import com.andrew.googleplay.bean.AppInfoBean;
import com.andrew.googleplay.utils.BitmapHelper;
import com.andrew.googleplay.utils.Constans;
import com.andrew.googleplay.utils.StringUtils;
import com.andrew.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.holder
 * @创建者: 谢康
 * @创建时间: 2016/11/23 下午 21:27
 * @描述: 应用详情页面的应用信息的holder
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class AppDetailInfoHolder extends BaseHolder<AppInfoBean> {
    @ViewInject(R.id.app_detail_info_iv_icon)
    private ImageView mIvIcon;
    @ViewInject(R.id.app_detail_info_rb_star)
    private RatingBar mRbStar;
    @ViewInject(R.id.app_detail_info_tv_name)
    private TextView  mTvName;
    @ViewInject(R.id.app_detail_info_tv_downloadnum)
    private TextView  mTvDownloadnum;
    @ViewInject(R.id.app_detail_info_tv_version)
    private TextView  mTvVersion;
    @ViewInject(R.id.app_detail_info_tv_time)
    private TextView  mTvTime;
    @ViewInject(R.id.app_detail_info_tv_size)
    private TextView  mTvSize;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_info, null);

        ViewUtils.inject(this, view);

        return view;
    }

    @Override
    protected void refreshUI(AppInfoBean data) {
        //设置数据
        mTvName.setText(data.name);
        mRbStar.setRating(data.stars);
        String downloadNum = UIUtils.getString(R.string.app_detail_info_downloadnum,
                                               data.downloadNum);
        String version = UIUtils.getString(R.string.app_detail_info_version, data.version);
        String date    = UIUtils.getString(R.string.app_detail_info_time, data.date);
        String size    = UIUtils.getString(R.string.app_detail_info_size,
                                           StringUtils.formatFileSize(data.size));
        mTvDownloadnum.setText(downloadNum);
        mTvVersion.setText(version);
        mTvTime.setText(date);
        mTvSize.setText(size);

        //设置图片
        BitmapHelper.display(mIvIcon, Constans.IMAGE_BASE_URL + data.iconUrl);
    }
}
