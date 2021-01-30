package me.wuxie.wakeshow.wakeshow.ui.component;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.ui.Container;
import org.bukkit.entity.LivingEntity;
/**
 * 生物GUI血条
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WHealth extends Component {
    /** 实时显示的对应生物 */
    @Getter
    private LivingEntity livingEntity;
    /** 血条背景图片 */
    @Getter
    private String texture;
    /** 血条图片 */
    @Getter
    private String cover;
    /** 竖向显示(贴图也需要竖向) */
    @Getter
    private boolean vertical = false;

    /**
     * 构造器
     * @param parent 父容器
     * @param id ID
     * @param x x
     * @param y y
     * @param w w
     * @param h h
     * @param texture 血条背景图片
     * @param cover 血条图片
     * @param livingEntity 血条对应的生物
     */
    public WHealth(Container parent, String id, int x, int y, int w, int h, String texture, String cover, LivingEntity livingEntity) {
        super(parent, id, x, y);
        this.w = w;
        this.h = h;
        this.livingEntity = livingEntity;
        this.texture = texture;
        this.cover = cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
        beforeUpdate();
    }

    public void setTexture(String texture) {
        this.texture = texture;
        beforeUpdate();
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
        beforeUpdate();
    }

    public void setLivingEntity(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
        beforeUpdate();
    }
}
