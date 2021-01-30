package me.wuxie.wakeshow.wakeshow.ui.hudcomponent;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.util.TickUtil;
import org.bukkit.entity.Player;
/**
 * HUD冷却条
 * @see me.wuxie.wakeshow.wakeshow.ui.component.WCooldingTag
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WHudCooldingTag extends HudComponent {
    @Getter
    private String texture;
    @Getter private String cooldingCover;
    // 竖向
    @Getter private boolean vertical = false;
    // 填充式
    @Getter private boolean stuff = false;
    // 已经冷却的时间
    private int currentTime;
    // 计时多少秒
    @Getter private int maxTime;
    // 标记时间(当update时，将会更新currentTime，以此作为衡量标准)
    @Getter
    private int signTime;
    /**
     * @param owner 玩家
     * @param id id
     * @param x 坐标
     * @param y 坐标
     * @param w 宽
     * @param h 高
     * @param currentTime 当前时间进度
     * @param maxTime 最大时间
     * @param texture 背景
     * @param cooldingCover 冷却蒙版
     */
    public WHudCooldingTag(Player owner, String id, int x, int y, int w, int h, int currentTime, int maxTime, String texture, String cooldingCover) {
        super(owner,id, x, y,w,h);
        this.texture =texture;
        this.cooldingCover =cooldingCover;
        this.currentTime =currentTime;
        this.maxTime =maxTime;
        this.signTime = TickUtil.getTick();
    }

    public boolean coolingIsOver(){
        updateTime();
        return TickUtil.getTick() >= maxTime+signTime-currentTime;
    }
    /**
     * 更新当前时间
     */
    public void updateTime() {
        int time = TickUtil.getTick();
        currentTime = maxTime - (signTime - currentTime + maxTime - time);
        signTime = time;
    }

    public void setCooldingCover(String cooldingCover) {
        this.cooldingCover = cooldingCover;
        //update();
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
        updateTime();
    }

    /**
     * 重设当前冷却时间
     * @param currentTime 当前冷却
     */
    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
        // 然后将标记时间重新设定
        this.signTime = TickUtil.getTick();
        updateTime();
    }

    public int getCurrentTime() {
        updateTime();
        return currentTime;
    }

    public void setTexture(String texture) {
        this.texture = texture;
        //update();
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
        //update();
    }

    public void setStuff(boolean stuff) {
        this.stuff = stuff;
        //update();
    }
}
