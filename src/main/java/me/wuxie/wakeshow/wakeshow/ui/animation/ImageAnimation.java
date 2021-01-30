package me.wuxie.wakeshow.wakeshow.ui.animation;

/**
 * 图像动画（在gui动画结束时播放）
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class ImageAnimation {
    /** 动画的tick数(执行多少次draw后停止,越小越快,越大越慢,0禁用动画)*/
    public int animationFrame = 0;
    /** 停止在结束状态多少tick，回转时等待的tick数，针对图片*/
    public int backDelayFrame = 0;
    /** 旋转开始的角度(累计值)*/
    public float rotate = 0.0f;
    /** 结束的角度(正数右转/负数左转)*/
    public float rotateTo = 0.0f;
    /** 结束时 0 回旋，1无限旋转，2帧播放结束停止旋转*/
    public int rotateModel = 2;
    /** 开始的透明度(累计值)*/
    public float alpha = 1.0f;
    /**结束的透明度，最终透明度*/
    public float alphaTo=1.0f;
    /** 结束时透明度回调(否，播放完停止透明度变化，是，播放完，等待backDelayFrame tick，倒回去)*/
    public boolean alphaBack = false;
    /**
     * 开始的大小(小于0时为0)(累计值)
     * 小于1，小放大，大于1，大缩小
     * */
    public float scale = 1.0f;
    /** 结束的大小*/
    public float scaleTo=1.0f;
    /** 结束时大小回调(否，播放完停止大小变化，是，播放完，等待backDelayFrame tick，倒回去)*/
    public boolean scaleBack = false;
    /** 坐标移动(原点加减)*/
    public int moveX = 0;
    /** 坐标移动(原点加减)*/
    public int moveY = 0;
    /**是否返回*/
    public boolean XYBack = false;

    @Override
    public String toString() {
        return animationFrame+","+backDelayFrame+","+rotate+","+rotateTo+","+rotateModel+","+alpha+","+alphaTo+","+alphaBack+","+scale+","+scaleTo+","+scaleBack+","+moveX+","+moveY+","+XYBack;
    }
}
