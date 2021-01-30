package me.wuxie.wakeshow.wakeshow.ui.hudcomponent;

import lombok.Getter;
import org.bukkit.entity.Player;
/**
 * HUD按钮
 * @see me.wuxie.wakeshow.wakeshow.ui.component.WButton
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WHudButton extends HudComponent {
    @Getter
    private String name;
    @Getter
    private String url1;
    @Getter
    private String url2;
    @Getter
    private String url3;
    @Getter
    private  boolean canPress = true;

    public WHudButton(Player owner, String id, int x, int y, int w, int h, String name, String url1, String url2, String url3) {
        super(owner,id, x, y, w, h);
        this.name = name;
        this.url1 = url1;
        this.url2 = url2;
        this.url3 = url3;
    }

    public void setName(String name) {
        this.name = name;
        //update();
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
        //update();
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
        //update();
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
        //update();
    }

    public void setCanPress(boolean canPress) {
        this.canPress = canPress;
        //update();
    }
}
