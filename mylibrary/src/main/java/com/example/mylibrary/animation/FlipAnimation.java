package com.example.mylibrary.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class FlipAnimation extends Animation {
    private final float mFromDegrees;//起始的度数
    private final float mToDegrees;//要到达的度数
    private final float mCenterX;// 水平方向X轴的位置
    private final float mCenterY;//Y轴的位置
    private Camera mCamera;


    /**
     * 构造器
     *
     * @param fromDegrees
     * @param toDegrees
     * @param centerX
     * @param centerY
     */
    public FlipAnimation(float fromDegrees, float toDegrees, float centerX, float centerY) {
        this.mFromDegrees = fromDegrees;
        this.mToDegrees = toDegrees;
        this.mCenterX = centerX;
        this.mCenterY = centerY;
    }

    /**
     * 初始化屏幕宽度创建Camera
     *
     * @param width
     * @param height
     * @param parentWidth
     * @param parentHeight
     */
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    /**
     * 动画效果
     */
    public void applyTransformation(float interpolatedTime, Transformation transformation) {
        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);//总度数

        /**
         * 获取值
         */
        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;

        /**
         * 对图片进行处理
         * Translate           平移变换
         Rotate                旋转变换
         Scale                  缩放变换
         Skew                  错切变换
         */
        final Matrix matrix = transformation.getMatrix();
        camera.save();  //保存当前状态 ，与restore是成对出现的
        camera.rotateY(degrees);//Y轴旋转
        camera.getMatrix(matrix);//得到设置后的matrix

        camera.restore();//回复当前状态

        /**
         * 下面两句标识以动画中心点为中央部分
         */
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }


}
