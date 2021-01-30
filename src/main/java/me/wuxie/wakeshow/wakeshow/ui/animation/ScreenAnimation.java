package me.wuxie.wakeshow.wakeshow.ui.animation;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * gui动画
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class ScreenAnimation {
    /** 播放速度(执行多少 tick 后停止,越小越快,越大越慢)*/
    public int animationFrame = 0;

    /** 旋转开始角度(累计值)*/
    public float rotate = 0.0f;
    /** 圈数(正数右转/负数左转)，一圈360°*/
    public int rotateTo = 0;

    /** 开始的透明度(累计值)*/
    public float alpha = 0.0f;
    /** 结束的透明度，最终透明度是1.0*/
    public float alphaTo=0.0f;

    /**
     * 开始的大小(结束的大小默认1)(累计值)
     * 小于1，小放大，大于1，大缩小
     * */
    public float scale = 1.0f;
}
