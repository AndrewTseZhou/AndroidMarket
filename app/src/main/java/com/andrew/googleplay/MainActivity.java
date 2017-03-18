package com.andrew.googleplay;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andrew.googleplay.factory.FragmentFactory;
import com.andrew.googleplay.fragment.DrawerAboutFragment;
import com.andrew.googleplay.fragment.DrawerHomeFragment;
import com.andrew.googleplay.fragment.DrawerSettingFragment;
import com.andrew.googleplay.fragment.TabStripBaseFragment;
import com.andrew.googleplay.utils.UIUtils;
import com.astuetz.PagerSlidingTabStrip;

import static com.andrew.googleplay.R.color.tab_text_selected;

public class MainActivity extends BaseActivity
        implements ViewPager.OnPageChangeListener, ListView.OnItemClickListener {
    private Toolbar               mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;//抽屉开关的控件
    private DrawerLayout          mDrawerLayout;
    private FrameLayout           mDrawerSlideContainer;
    private FrameLayout           mContentContainer;
    private ListView              mDrawerSlide;
    private PagerSlidingTabStrip  mTabStrip;
    private ViewPager             mViewPager;
    private String[]              mMainTitles;
    private String[]              mDrawerTitles;
    private int[] mIvIcons = {R.mipmap.ic_home_slide,
                              R.mipmap.ic_setting,
                              R.mipmap.ic_theme,
                              R.mipmap.ic_scans,
                              R.mipmap.ic_feedback,
                              R.mipmap.ic_updates,
                              R.mipmap.ic_about,
                              R.mipmap.ic_exit};
    private DrawerLayoutSlideAdapter mDrawerAdapter;
    private CharSequence             mDrawerTitle;
    private CharSequence             mTitle;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mContentContainer = (FrameLayout) findViewById(R.id.content_container);
        mDrawerSlide = (ListView) findViewById(R.id.drawer_slide);
        mTabStrip = (PagerSlidingTabStrip) findViewById(R.id.main_tabs);
        mViewPager = (ViewPager) findViewById(R.id.main_pager);

        //设置DrawerLayout左侧抽屉部分的背景颜色
        mDrawerSlide.setBackgroundColor(Color.GRAY);
//        mDrawerSlideContainer.setBackgroundColor(Color.GRAY);
//        设置指针文本的颜色
//        int normalColor   = getResources().getColor(R.color.tab_text_normal);
//        int selectedColor = getResources().getColor(tab_text_selected);
        int normalColor   = UIUtils.getColor(R.color.tab_text_normal);
        int selectedColor = UIUtils.getColor(tab_text_selected);
        mTabStrip.setTextColor(normalColor, selectedColor);
    }

    @Override
    protected void initToolbar() {
        //获取Toolbar
        mToolbar = (Toolbar) findViewById(R.id.app_main_bar);

        //设置Toolbar
        mToolbar.setTitle("应用商店");//设置title部分
        mToolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(mToolbar);

        //初始化DrawerToggle
        mDrawerToggle = new ActionBarDrawerToggle(this,
                                                  mDrawerLayout,
                                                  mToolbar,
                                                  R.string.main_des_drawer_open,
                                                  R.string.main_des_drawer_close) {
            public void onDrawerClosed(View view) {
                mToolbar.setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                mToolbar.setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        //使用DrawerToggle
        //设置DrawerLayout的监听
        mDrawerLayout.addDrawerListener(mDrawerToggle);
//        if (savedInstanceState == null)
//        {
//            selectItem(0);
//        }
        mDrawerToggle.syncState();//同步状态
    }

    @Override
    protected void initData() {
        //初始化title
        mMainTitles = UIUtils.getStringArray(R.array.main_titles);
        mDrawerTitles = UIUtils.getStringArray(R.array.drawlayout_titles);

        //给ViewPager设置Adapter
        mViewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager()));

        //给tabstrip设置ViewPager
        mTabStrip.setViewPager(mViewPager);

        // 设置ViewPager的监听
        mTabStrip.setOnPageChangeListener(this);

        //给DrawLayout左侧部分设置Adapter
        mDrawerAdapter = new DrawerLayoutSlideAdapter();
        mDrawerSlide.setAdapter(mDrawerAdapter);
        mDrawerSlide.setOnItemClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 当选中的时候去加载数据
        TabStripBaseFragment fragment = FragmentFactory.getFragment(position);
        fragment.loadData();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * DrawerLayout左侧抽屉菜单项的点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    private void selectItem(int position) {
        FragmentTransaction ft       = getSupportFragmentManager().beginTransaction();
        Fragment            fragment = null;

        mContentContainer.removeAllViews();
        switch (position)
        {
            case 0:
                fragment = new DrawerHomeFragment();
                break;
            case 1:
                fragment = new DrawerSettingFragment();
                break;
            case 2:
                break;
            case 3:

                break;
            case 4:
                break;
            case 5:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //设置标题
                builder.setTitle("警告");
                //设置信息
                builder.setMessage("确认检查更新?如果使用的是流量，可能会产生流量费用!");
                //设置确定和取消按钮
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        //进度对话框
                        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
                        pd.setTitle("检查更新");
                        pd.setMessage("正在检查更新，请稍后……");
                        pd.show();
                        pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                //进度条对话框
                                final ProgressDialog pd2 = new ProgressDialog(MainActivity.this);
                                pd2.setTitle("请稍后");
                                //设置进度的样式
                                pd2.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                pd2.setMessage("正在下载");
                                //设置进度条的最大值
                                pd2.setMax(100);
                                pd2.show();

                                pd2.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        Toast.makeText(MainActivity.this,"下载完成",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                new Thread() {
                                    @Override
                                    public void run() {
                                        try
                                        {
                                            for (int i = 0; i <= 100; i++)
                                            {
                                                Thread.sleep(150);
                                                pd2.setProgress(i);
                                            }
                                        }
                                        catch (InterruptedException e)
                                        {
                                            e.printStackTrace();
                                        }
                                        pd2.dismiss();
                                    }
                                }.start();
                            }
                        });
                        new Thread() {
                            @Override
                            public void run() {
                                try
                                {
                                    Thread.sleep(3000);
                                }
                                catch (InterruptedException e)
                                {
                                    e.printStackTrace();
                                }
                                pd.dismiss();
                            }
                        }.start();
                    }
                });
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                //必须调用show()方法
                builder.show();
                break;
            case 6:
                fragment = new DrawerAboutFragment();
                break;
            case 7:
                finish();
                break;
        }

        if (fragment != null)
        {
            ft.replace(R.id.content_container, fragment).commit();
        }
        mDrawerSlide.setItemChecked(position, true);
        if (position != 0)
        {
            setTitle(mDrawerTitles[position]);
        }
        else
        {
            setTitle("我的应用商店");
        }
        mDrawerLayout.closeDrawer(mDrawerSlide);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        mToolbar.setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //当onRestoreInstanceState被调用的时候
        //同步DrawerToggle的状态
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 忽略一切对DrawerToggle的设置改变
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    class MainFragmentPagerAdapter extends FragmentPagerAdapter {

        public MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.getFragment(position);
        }

        @Override
        public int getCount() {
            if (mMainTitles != null)
            {
                return mMainTitles.length;
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mMainTitles != null)
            {
                return mMainTitles[position];
            }
            return super.getPageTitle(position);
        }
    }

    class DrawerLayoutSlideAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mDrawerTitles != null)
            {
                return mDrawerTitles.length;
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mDrawerTitles != null)
            {
                return mDrawerTitles[position];
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DrawerSlideHolder holder = null;
            if (convertView == null)
            {
                convertView = View.inflate(UIUtils.getContext(), R.layout.item_drawer, null);
                holder = new DrawerSlideHolder();
                convertView.setTag(holder);

                holder.iv = (ImageView) convertView.findViewById(R.id.item_drawer_slide_iv);
                holder.tv = (TextView) convertView.findViewById(R.id.item_drawer_slide_tv);
            }
            else
            {
                holder = (DrawerSlideHolder) convertView.getTag();
            }

            holder.iv.setImageResource(mIvIcons[position]);
            holder.tv.setText(mDrawerTitles[position]);

            return convertView;
        }
    }

    class DrawerSlideHolder {
        ImageView iv;
        TextView  tv;
    }

    class MainPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mMainTitles != null)
            {
                return mMainTitles.length;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView textView = new TextView(UIUtils.getContext());
            textView.setText(mMainTitles[position]);
            container.addView(textView);

            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mMainTitles != null)
            {
                return mMainTitles[position];
            }
            return super.getPageTitle(position);
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
                //如果返回true 自己响应
                if (mDrawerToggle.onOptionsItemSelected(item))
                {
                    return true;
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //设置标题
                builder.setTitle("警告");
                //设置信息
                builder.setMessage("确认退出?");
                //设置确定和取消按钮
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                });
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                //必须调用show()方法
                builder.show();
                break;
        }

        return super.onKeyDown(keyCode, event);
    }
}
