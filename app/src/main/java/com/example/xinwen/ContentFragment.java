package com.example.xinwen;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mylibrary.interfaces.ScreenShortable;






/**
 * Created by Konstantin on 22.12.2014.
 */
public class ContentFragment extends Fragment implements ScreenShortable {
    public static final String CLOSE = "关闭";
    public static final String BUILDING = "创建";
    public static final String BOOK = "书本";
    public static final String PAINT = "交点";
    public static final String CASE = "案例";
    public static final String SHOP = "商品";
    public static final String PARTY = "派对";
    public static final String MOVIE = "电影";

    private View rootView;
    private ImageView mImageView;
    private int res;
    private Bitmap bitmap;

    public static ContentFragment newInstance(int resId) {
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        contentFragment.setArguments(bundle);//设置Arguments
        return contentFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.rootView = view.findViewById(R.id.container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());//获取Arguments的值
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        mImageView = (ImageView) view.findViewById(R.id.image_content);
        mImageView.setClickable(true);
        mImageView.setFocusable(true);
        mImageView.setImageResource(res);
        return view;
    }

    /**
     * 设置bitmap
     * 耗时操作
     */
    @Override
    public void takeScreenShort() {
        Thread thread=new Thread(){
            @Override
            public void run() {
                Bitmap bitmap=Bitmap.createBitmap(rootView.getWidth(),rootView.getHeight(),Bitmap.Config.ARGB_8888);
                Canvas canvas=new Canvas(bitmap);
                rootView.draw(canvas);
                ContentFragment.this.bitmap=bitmap;
            }
        };
        thread.start();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
