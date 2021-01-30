package me.wuxie.wakeshow.wakeshow.ui.animation;

/**
 * 动画节点
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class ImageAnimationNode{
    /**动画tick数(执行多少次draw后停止,越小越快,越大越慢,0禁用动画)*/
    public int animationFrame = 0;
    /** 结束的角度(正数右转/负数左转)
     * 这为节点值(每个节点值会从上一个节点开始变化)
     * -9999 的意思为当前节点不变
     * */
    public float rotateTo = -9999;
    /** 结束的透明度，最终透明度
     * 这为节点值(每个节点值会从上一个节点开始变化)
     * -9999 的意思为当前节点不变
     * */
    public float alphaTo=-9999;
    /** 结束的大小
     * 这为节点值(每个节点值会从上一个节点开始变化)
     * -9999 的意思为当前节点不变
     * */
    public float scaleTo=-9999;
    /** 坐标移动(原点加减) */
    public int moveX = 0;
    /** 坐标移动(原点加减) */
    public int moveY = 0;
    @Override
    public String toString() {
        return animationFrame+","+rotateTo+","+alphaTo+","+scaleTo+","+moveX+","+moveY;
    }
}
