package me.wuxie.wakeshow.wakeshow.ui.hudcomponent;

import lombok.Getter;
import org.bukkit.entity.Player;
/**
 * HUD图像
 * @see me.wuxie.wakeshow.wakeshow.ui.component.WImage
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WHudImage extends HudComponent {
    @Getter
    private String url1;
    public WHudImage(Player owner, String id, String url1, int x, int y, int w, int h) {
        super(owner,id, x, y,w,h);
        this.url1 = url1;
    }
    public void setUrl1(String url1) {
        this.url1 = url1;
        //update();
    }
}