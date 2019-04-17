package com.example.mylibrary.util;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.mylibrary.interfaces.Resourceble;

import java.util.ArrayList;
import java.util.List;

import com.example.mylibrary.R;
import com.example.mylibrary.animation.FlipAnimation;

import com.example.mylibrary.interfaces.ScreenShortable;

import static android.view.FrameMetrics.ANIMATION_DURATION;


/**
 * Created by Konstantin on 12.01.2015.
 */
public class ViewAnimationUtils<T extends Resourceble> {
    /**
     * 切换隐藏与显示的效果
     */
    private final int ANIMATIOsN_DURATION = 10;
    public static final int CIRCULAR_REVEAL_ANIMATION_DURATION = 20;

    private Activity activity;
    private List<T> list;
    private List<View> viewList = new ArrayList<View>();  //View集合
    private ScreenShortable screenShortable;//获取图片
    private DrawerLayout drawerLayout;
    private ViewAnimatorListener viewAnimatorListener;//实现定义接口

    /**
     * 构造器
     */
    public ViewAnimationUtils(Activity activity, List<T> list, ScreenShortable screenShortable, DrawerLayout drawerLayout, ViewAnimatorListener viewAnimatorListener) {
        this.activity = activity;
        this.list = list;
        this.screenShortable = screenShortable;
        this.drawerLayout = drawerLayout;
        this.viewAnimatorListener = viewAnimatorListener;
    }

    /**
     * 显示菜单的数量
     */
    public void showMenuCount() {
        setViewsClickable(false);//刚显示个数时view是出于false的状态
        viewList.clear();//清空组件
        double size = list.size();
        for (int i = 0; i < size; i++) {
            View viewMenu = activity.getLayoutInflater().inflate(R.layout.menu_list_item, null);
            final int finalI = i;
            /**
             * view的点击事件
             */
            viewMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int[] localtion = {0, 0};//用数组坐标记录位置
                    v.getLocationOnScreen(localtion);//垂直
                    switchItem(list.get(finalI), localtion[1] + v.getHeight() / 2);     //点击后调用switchItem隐藏
                }
            });
            //得到资源并设置(重写)
            ((ImageView) viewMenu.findViewById(com.example.mylibrary.R.id.menu_item_image)).setImageResource(list.get(i).getImageRes());
            viewMenu.setVisibility(View.GONE);
            viewMenu.setEnabled(false);
            viewList.add(viewMenu);//添加list中
            viewAnimatorListener.addViewToContainer(viewMenu);//添加到动画接口中

            final double position = i;
            final double delay = 3 * ANIMATION_DURATION * (position / size);
            /**
             * 耗时操作
             */
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (position < viewList.size()) {
                        animateView((int) position);
                    }
                    if (position == viewList.size() - 1) {
                        screenShortable.takeScreenShort();
                        setViewsClickable(true);
                    }
                }
            }, (long) delay);


        }

    }

    /**
     * 设置View
     */
    private void animateView(int position) {
        final View view = viewList.get(position);
        view.setVisibility(View.VISIBLE);//可见
        FlipAnimation rotation = new FlipAnimation(90, 0, 0.0f, view.getHeight() / 2.0f);//设置动画效果
        rotation.setDuration(ANIMATION_DURATION);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        //动画监听
        rotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();//结束后清除动画效果
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }


    /**
     * 点击的Item
     */
    private void switchItem(Resourceble slideMenuItem, int topPosition) {
        this.screenShortable = viewAnimatorListener.onSwitch(slideMenuItem, screenShortable, topPosition);
        //点击后隐藏viewList
        hideMenuContent();
    }

    /**
     * 隐藏menu
     */
    private void hideMenuContent() {
        setViewsClickable(false);
        double size = list.size();
        //隐藏全部
        for (int i = list.size(); i >= 0; i--) {
            final double position = i;//记录在viewList中点击的position作为头部，添加动画效果
            final double delay = 3 * ANIMATION_DURATION * (position / size);    //线程时间（其实这一步没必要，线程的时间可以直接写死）
            //耗时操作应放在handler里面操作
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (position < viewList.size()) {
                        //设置隐藏时的动画
                        animateHideView((int) position);
                    }
                }
            }, (long) delay);
        }
    }

    /**
     * 设置view隐藏时的动画
     */
    private void animateHideView(final int position) {
        final View view = viewList.get(position);//得到点击的view
        //0表示正在处于的状态为0°，90为操作后的度数，0.0f表示起始精确位置，centerY为view高度的二分之一
        FlipAnimation rotation = new FlipAnimation(0, 90, 0.0f, view.getHeight() / 2.0f);//设置动画
        rotation.setDuration(ANIMATION_DURATION);   //设置动画时间
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            /**
             * 结束时
             * @param animation
             */
            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                view.setVisibility(View.INVISIBLE);//隐藏view
                if (position == viewList.size() - 1) //防止越界
                {
                    viewAnimatorListener.enableHomeButton();
                    drawerLayout.closeDrawers();//关闭drawers
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(rotation);//开始动画
    }


    /**
     * 设置控件是否可用
     */
    private void setViewsClickable(boolean clickable) {
        //点击以后view不可再重新点击
        viewAnimatorListener.disableHomeButton();
        /**
         * 点击第几个view
         */
        for (View view : viewList) {
            view.setEnabled(clickable);
        }

    }

    /**
     * 定义接口
     */
    public interface ViewAnimatorListener {
        //点击MenuItem

        public ScreenShortable onSwitch(Resourceble slideMenuItem, ScreenShortable screenShortable, int position);

        public void disableHomeButton();

        public void enableHomeButton();

        public void addViewToContainer(View view);
    }
}
