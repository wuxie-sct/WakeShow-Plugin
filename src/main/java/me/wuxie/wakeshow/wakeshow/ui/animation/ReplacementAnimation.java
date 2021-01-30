package me.wuxie.wakeshow.wakeshow.ui.animation;

/**
 * 按钮/勾选框动画
 * 更替动画
 * 按钮3个状态，勾选框2个状态
 * 每个状态在更替时，会重新播放动画
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class ReplacementAnimation {
    /** 动画tick数(执行多少次draw后停止,越小越快,越大越慢,0禁用动画) */
    public int animationFrame = 0;
    /** 动画tick数放完了，停止在结束状态多少 tick，回转时等待的 tick 数*/
    public int backDelayFrame = 0;
    /** 旋转开始角度(累计值)*/
    public float rotate = 0.0f;
    /** 圈数(正数右转/负数左转)，一圈360°*/
    public float rotateTo = 0.0f;
    /**
     * 是否回旋(否，播放完停止旋转，是，播放完，等待backDelayFrame tick，倒回去)
     *  0 回旋，1无限旋转，2帧播放结束停止旋转
     *  */
    public int rotateModel = 2;
    /** 开始的透明度(累计值，默认达到alphaTo时返回，依次循环)*/
    public float alpha = 1.0f;
    /** 结束的透明度，最终透明度是1.0*/
    public float alphaTo=1.0f;
    /** 透明度回调(否，播放完停止透明度变化，是，播放完，等待backDelayFrame tick，倒回去)*/
    public boolean alphaBack = false;

    /** 开始的大小(累计值)
     * 小于1，小放大，大于1，大缩小
     * */
    public float scale = 1.0f;
    /** 结束的大小*/
    public float scaleTo=1.0f;
    /** 大小回调(否，播放完停止大小变化，是，播放完，等待backDelayFrame tick，倒回去)*/
    public boolean scaleBack = false;

    @Override
    public String toString() {
        if(animationFrame>0) {
            return animationFrame + "," + backDelayFrame + "," + rotate + "," + rotateTo + "," + rotateModel + "," + alpha + "," + alphaTo + "," + alphaBack + "," + scale + "," + scaleTo + "," + scaleBack;
        }
        return "0";
    }
}
