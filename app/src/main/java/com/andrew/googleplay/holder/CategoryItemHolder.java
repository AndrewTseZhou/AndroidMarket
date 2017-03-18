package com.andrew.googleplay.holder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andrew.googleplay.R;
import com.andrew.googleplay.bean.CategoryBean;
import com.andrew.googleplay.utils.BitmapHelper;
import com.andrew.googleplay.utils.Constans;
import com.andrew.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * @项目名: GooglePlay
 * @包名: com.andrew.googleplay.holder
 * @创建者: 谢康
 * @创建时间: 2016/11/21 下午 14:14
 * @描述: TODO
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */

public class CategoryItemHolder extends BaseHolder<CategoryBean> {
    @ViewInject(R.id.item_category_icon_1)
    private ImageView mIv1;
    @ViewInject(R.id.item_category_icon_2)
    private ImageView mIv2;
    @ViewInject(R.id.item_category_icon_3)
    private ImageView mIv3;
    @ViewInject(R.id.item_category_name_1)
    private TextView  mTv1;
    @ViewInject(R.id.item_category_name_2)
    private TextView  mTv2;
    @ViewInject(R.id.item_category_name_3)
    private TextView  mTv3;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_category, null);

        ViewUtils.inject(this, view);

        return view;
    }

    @Override
    protected void refreshUI(CategoryBean data) {
        //设置标题
        mTv1.setText(data.name1);
        mTv2.setText(data.name2);
        mTv3.setText(data.name3);

        //设置默认图片
        mIv1.setImageResource(R.mipmap.ic_default);
        mIv2.setImageResource(R.mipmap.ic_default);
        mIv3.setImageResource(R.mipmap.ic_default);

        //去网络中获取
        display(mIv1, data.url1);
        display(mIv2, data.url2);
        display(mIv3, data.url3);
    }

    private void display(ImageView iv, String url) {
        if (!TextUtils.isEmpty(url)) {
            //显示item对应的view
            //必须有此操作 否则在隐藏item后 会出现部分item显示不了
            ViewParent parent = iv.getParent();
            if (parent != null && parent instanceof View) {
                ((View) parent).setVisibility(View.VISIBLE);
            }
            BitmapHelper.display(iv, Constans.IMAGE_BASE_URL + url);
        } else {
            //当url为空的时候
            //隐藏item对应的view
            ViewParent parent = iv.getParent();
            if (parent != null && parent instanceof View) {
                ((View) parent).setVisibility(View.INVISIBLE);
            }
        }
    }

    @OnClick(R.id.item_category_item_1)
    public void clickItem1(View view) {
        Toast.makeText(UIUtils.getContext(), mData.name1, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.item_category_item_2)
    public void clickItem2(View view) {
        Toast.makeText(UIUtils.getContext(), mData.name2, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.item_category_item_3)
    public void clickItem3(View view) {
        Toast.makeText(UIUtils.getContext(), mData.name3, Toast.LENGTH_SHORT).show();
    }
}
