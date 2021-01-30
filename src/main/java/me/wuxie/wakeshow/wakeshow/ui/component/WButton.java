package me.wuxie.wakeshow.wakeshow.ui.component;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.ui.Container;
import me.wuxie.wakeshow.wakeshow.ui.animation.ReplacementAnimation;
/**
 * 按钮
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class WButton extends Component {
    /**
     * 显示在按钮上的文字
     */
    @Getter
    private String name;
    /**
     * 平常状态下的图片
     */
    @Getter
    private String url1;
    /**
     * 悬停状态下的图片
     */
    @Getter
    private String url2;
    /**
     * 按下状态的图片
     */
    @Getter
    private String url3;
    /**
     * 是否能按下
     */
    @Getter
    private  boolean canPress = true;
    /**
     * 平常状态下的图片动画
     */
    @Getter
    private ReplacementAnimation normalAnimation;
    /**
     * 悬停状态下的图片动画
     */
    @Getter
    private ReplacementAnimation hoverAnimation;
    /**
     * 按下状态下的图片
     */
    @Getter
    private ReplacementAnimation pressAnimation;

    /**
     * 按钮构造器
     *
     * @param parent 父容器
     * @param id id
     * @param name 显示在按钮上的文字
     * @param url1 平常状态下的图片
     * @param url2 悬停状态下的图片
     * @param url3 按下状态的图片
     * @param x 坐标
     * @param y 坐标
     */
    public WButton(Container parent, String id,String name,String url1,String url2,String url3, int x, int y) {
        super(parent, id, x, y);
        this.name = name;
        this.url1 = url1;
        this.url2 = url2;
        this.url3 = url3;
    }

    public void setNormalAnimation(ReplacementAnimation normalAnimation) {
        this.normalAnimation = normalAnimation;
        beforeUpdate();
    }

    public void setHoverAnimation(ReplacementAnimation hoverAnimation) {
        this.hoverAnimation = hoverAnimation;
        beforeUpdate();
    }

    public void setPressAnimation(ReplacementAnimation pressAnimation) {
        this.pressAnimation = pressAnimation;
        beforeUpdate();
    }

    public void setName(String name) {
        this.name = name;
        beforeUpdate();
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
        beforeUpdate();
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
        beforeUpdate();
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
        beforeUpdate();
    }

    public void setCanPress(boolean canPress) {
        this.canPress = canPress;
        beforeUpdate();
    }
}
