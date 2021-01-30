package me.wuxie.wakeshow.wakeshow.ui.component;

import lombok.Getter;
import lombok.Setter;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.ui.Container;
import me.wuxie.wakeshow.wakeshow.util.TickUtil;
/**
 * 冷却条
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WCooldingTag extends Component {
    /** 背景贴图 */
    @Getter private String texture;
    /** 蒙版贴图(冷却条) */
    @Getter private String cooldingCover;
    /** 竖向 */
    @Getter private boolean vertical = false;
    /** 填充式 */
    @Getter private boolean stuff = false;
    /** 已经冷却的时间 */
    private int currentTime;
    /** 计时多少秒 */
    @Getter private int maxTime;
    /** 标记时间(当update时，将会更新currentTime，以此作为衡量标准) */
    @Getter
    private int signTime;
    /**
     * 冷却条构造器
     *
     * @param parent 父容器
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
    public WCooldingTag(Container parent, String id, int x, int y, int w, int h,
                        int currentTime, int maxTime, String texture, String cooldingCover) {
        super(parent, id, x, y);
        this.w =w;
        this.h =h;
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

    public void setCooldingCover(String cooldingCover) {
        this.cooldingCover = cooldingCover;
        beforeUpdate();
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
        beforeUpdate();
    }

    /**
     * 重设当前冷却时间
     * @param currentTime 当前冷却
     */
    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
        // 然后将标记时间重新设定
        this.signTime = TickUtil.getTick();
        beforeUpdate();
    }

    public int getCurrentTime() {
        updateTime();
        return currentTime;
    }

    public void setTexture(String texture) {
        this.texture = texture;
        beforeUpdate();
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
        beforeUpdate();
    }

    public void setStuff(boolean stuff) {
        this.stuff = stuff;
        beforeUpdate();
    }
    @Override
    public void beforeUpdate(){
        updateTime();
        super.beforeUpdate();
    }

    /**
     * 更新当前时间
     */
    public void updateTime() {
        int time = TickUtil.getTick();
        currentTime = maxTime - (signTime - currentTime + maxTime - time);
        signTime = time;
    }
}
