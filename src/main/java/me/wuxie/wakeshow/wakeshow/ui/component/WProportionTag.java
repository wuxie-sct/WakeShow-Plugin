package me.wuxie.wakeshow.wakeshow.ui.component;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.ui.Container;

/**
 * 占比示意条
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WProportionTag extends Component {
    /** 比例 0-1*/
    @Getter
    private double proportion;
    /** 贴图背景*/
    @Getter
    private String texture;
    /** 比例贴图*/
    @Getter
    private String cover;
    /** 竖向*/
    @Getter private boolean vertical = false;

    /**
     *
     * @param parent 父容器
     * @param id id
     * @param x x
     * @param y y
     * @param w w
     * @param h h
     * @param proportion 比例 0-1
     * @param texture 贴图背景
     * @param cover 比例贴图
     */
    public WProportionTag(Container parent, String id, int x, int y, int w , int h, double proportion, String texture, String cover) {
        super(parent, id, x, y);
        this.w = w;
        this.h = h;
        this.proportion = proportion;
        this.texture = texture;
        this.cover = cover;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
        beforeUpdate();
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
        beforeUpdate();
    }

    public void setTexture(String texture) {
        this.texture = texture;
        beforeUpdate();
    }

    public void setCover(String cover) {
        this.cover = cover;
        beforeUpdate();
    }
}
