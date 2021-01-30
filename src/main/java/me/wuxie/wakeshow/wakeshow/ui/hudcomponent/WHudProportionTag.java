package me.wuxie.wakeshow.wakeshow.ui.hudcomponent;

import lombok.Getter;
import org.bukkit.entity.Player;
/**
 * HUD占比示意条
 * @see me.wuxie.wakeshow.wakeshow.ui.component.WProportionTag
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WHudProportionTag extends HudComponent {
    @Getter
    private double proportion;
    @Getter
    private String texture;
    @Getter
    private String cover;
    @Getter private boolean vertical = false;

    public WHudProportionTag(Player owner, String id, int x, int y, int w , int h, double proportion, String texture, String cover) {
        super(owner,id, x, y,w,h);
        this.proportion = proportion;
        this.texture = texture;
        this.cover = cover;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
        //update();
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
        //update();
    }

    public void setTexture(String texture) {
        this.texture = texture;
        //update();
    }

    public void setCover(String cover) {
        this.cover = cover;
        //update();
    }
}