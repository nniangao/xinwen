package com.example.xinwen;



import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.astuetz.PagerSlidingTabStrip;
import com.example.xinwen.fragment.BaseFragment;
import com.example.xinwen.fragment.TopFragment;



public class MainActivity extends AppCompatActivity  {
    private PagerSlidingTabStrip mPagerTab;
    private ViewPager mViewPager;
    private MyAdapter mAdapter;
    public BaseFragment fragment;

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


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



}


