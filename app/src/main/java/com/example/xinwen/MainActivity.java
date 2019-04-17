package com.example.xinwen;


import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.example.mylibrary.interfaces.Resourceble;
import com.example.mylibrary.interfaces.ScreenShortable;
import com.example.mylibrary.model.SlideMenuItem;
import com.example.mylibrary.util.ViewAnimationUtils;
import com.example.xinwen.fragment.BaseFragment;
import com.example.xinwen.fragment.TopFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewAnimationUtils.ViewAnimatorListener {
    private PagerSlidingTabStrip mPagerTab;
    private ViewPager mViewPager;
    private MyAdapter mAdapter;
    public BaseFragment fragment;
    private ContentFragment contentFragment;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<SlideMenuItem>();
    private ViewAnimationUtils viewAnimationUtils;
    private int res = R.layout.personal;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        fragment = new TopFragment();

        //拿到对应的控件
        mPagerTab = findViewById(R.id.ps_tab);
        mViewPager = findViewById(R.id.vp_news);

        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        // 导航栏绑定ViewPager
        mPagerTab.setViewPager(mViewPager);
        // 对用户滑动界面的监听。根据滑动到的页面，去读取改页面对应的信息
        mPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //监听到页面滑动了，就开始生产对应页面 ，比如滑动到了健康页面，就会加载健康类型的新闻信息，然后显示
                fragment = FragmentFactory.createFragment(position);


                contentFragment = ContentFragment.newInstance(R.layout.personal);
                int commit = getFragmentManager().beginTransaction().replace(R.id.container_frame, contentFragment).commit();
                drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawerLayout.setScrimColor(Color.TRANSPARENT);
                linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drawerLayout.closeDrawers();
                    }
                });
                setActionBar();
                createMenuList();
                viewAnimationUtils = new ViewAnimationUtils<>(this, list, contentFragment, drawerLayout, this);


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0) {
                    viewAnimationUtils.showMenuCount();
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    /**
     * 创建menu菜单
     */
    private void createMenuList() {
        SlideMenuItem menuitemOne = new SlideMenuItem(ContentFragment.CLOSE, R.drawable.icn_close);
        list.add(menuitemOne);
        SlideMenuItem menuitemTwo = new SlideMenuItem(ContentFragment.BUILDING, R.drawable.icn_1);
        list.add(menuitemTwo);
        SlideMenuItem menuitemthree = new SlideMenuItem(ContentFragment.BOOK, R.drawable.icn_2);
        list.add(menuitemthree);
        SlideMenuItem menuitemFour = new SlideMenuItem(ContentFragment.CASE, R.drawable.icn_3);
        list.add(menuitemFour);
        SlideMenuItem menuitemFive = new SlideMenuItem(ContentFragment.MOVIE, R.drawable.icn_4);
        list.add(menuitemFive);
        SlideMenuItem menuitemSix = new SlideMenuItem(ContentFragment.PAINT, R.drawable.icn_5);
        list.add(menuitemSix);
        SlideMenuItem menuitemSeven = new SlideMenuItem(ContentFragment.SHOP, R.drawable.icn_6);
        list.add(menuitemSeven);
        SlideMenuItem menuitemEight = new SlideMenuItem(ContentFragment.PARTY, R.drawable.icn_7);
        list.add(menuitemEight);

    }

    /**
     * 开始状态
     *
     * @param savedInstanceState
     * @param persistentState
     */
    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerToggle.syncState();
    }

    /**
     * 配置drawerToggle
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public ScreenShortable onSwitch(Resourceble slideMenuItem, ScreenShortable screenShortable, int position) {
        switch (slideMenuItem.getName()) {
            case ContentFragment.CLOSE:
                return screenShortable;
            default:
                return replaceFragment(screenShortable, position);
        }
    }

    /**
     * 切换图片操作
     *
     * @param screenShortable
     * @param position
     * @return
     */
    private ScreenShortable replaceFragment(ScreenShortable screenShortable, int position) {
        this.res = this.res == R.layout.personal ? R.layout.activity_main : R.layout.personal;
        View view = findViewById(R.id.container_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        io.codetail.animation.SupportAnimator animator = io.codetail.animation.ViewAnimationUtils.createCircularReveal(view, 0, position, 0, finalRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(ViewAnimationUtils.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShortable.getBitmap()));
        animator.start();//开始动画
        ContentFragment contentFragment = ContentFragment.newInstance(this.res);
        getFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }

    /**
     * 设置button不可用
     */
    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();
    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }


}


