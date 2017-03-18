package com.andrew.googleplay.utils;

import android.view.View;

import com.lidroid.xutils.BitmapUtils;

/**
 * @项目名: GooglePlay
 * @包名: org.andrew.googleplay.utils
 * @创建者: 谢康
 * @创建时间: 2015-5-6 下午2:27:06
 * @描述: 图片加载的工具类
 * *
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class BitmapHelper
{
	private static BitmapUtils	utils;

	static
	{
		utils = new BitmapUtils(UIUtils.getContext());
	}

	public static <T extends View> void display(T container, String uri)
	{
		utils.display(container, uri);
	}
}
