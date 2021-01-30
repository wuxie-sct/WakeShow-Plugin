package me.wuxie.wakeshow.wakeshow.ui.hudcomponent;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
/**
 * HUD文本列表
 * @see me.wuxie.wakeshow.wakeshow.ui.component.WTextList
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WHudTextList extends HudComponent {
    @Getter
    private List<String> content;
    @Getter
    private double scale = 1.0d;
    public WHudTextList(Player owner,String id, int x, int y, int w, int h, List<String> content) {
        super(owner,id, x, y,w,h);
        this.content = content;
    }

    public void setContent(List<String> content) {
        content=content==null?new ArrayList<>():content;
        this.content = content;
        //update();
    }

    public void setScale(double scale) {
        this.scale = scale;
        //update();
    }
}