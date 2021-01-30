package me.wuxie.wakeshow.wakeshow.ui.component;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.api.WuxieAPI;
import me.wuxie.wakeshow.wakeshow.api.event.ScreenDropItemEvent;
import me.wuxie.wakeshow.wakeshow.ui.ClickFunction;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.ui.Container;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;
import me.wuxie.wakeshow.wakeshow.ui.animation.ReplacementAnimation;
import me.wuxie.wakeshow.wakeshow.util.PlayerUtil;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * 丢弃域
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WDropField extends Component {
    /** 在丢弃域显示一段文本 */
    @Getter
    private String name;
    /** 平常状态贴图 */
    @Getter
    private String url1;
    /** 悬停状态贴图 */
    @Getter
    private String url2;
    /** 平常状态贴图动画 */
    @Getter
    private ReplacementAnimation normalAnimation;
    /** 悬停状态贴图动画 */
    @Getter
    private ReplacementAnimation hoverAnimation;

    /**
     *
     * @param parent 父容器
     * @param id ID
     * @param x x
     * @param y y
     * @param w w
     * @param h h
     */
    public WDropField(Container parent, String id, int x, int y,int w,int h) {
        super(parent, id, x, y);
        this.w = w;
        this.h = h;
        function = (b,p)->{
            WxScreen screen = getScreen();
            if(screen.getCursor()!=null&&!screen.getCursor().getType().equals(Material.AIR)){
                ScreenDropItemEvent dropItemEvent = new ScreenDropItemEvent(p,screen,screen.getCursor());
                Bukkit.getPluginManager().callEvent(dropItemEvent);
                if(dropItemEvent.isCancelled()){
                    WuxieAPI.updateGui(p);
                }else {
                    PlayerDropItemEvent event = PlayerUtil.dropItem(p,screen.getCursor());
                    if(event.isCancelled()){
                        WuxieAPI.updateGui(p);
                    }else {
                        screen.setCursor(null);
                    }
                }
            }
        };
    }

    @Override
    public void setFunction(ClickFunction function) {
    }

    /**
     *
     * @param parent 父容器
     * @param id ID
     * @param x x
     * @param y y
     * @param w w
     * @param h h
     * @param name 在丢弃域显示一段文本
     * @param url1 平常状态贴图
     * @param url2 悬停状态贴图动画
     */
    public WDropField(Container parent, String id, int x, int y, int w, int h, String name, String url1, String url2) {
        this(parent, id, x, y,w,h);
        this.name = name;
        this.url1 = url1;
        this.url2 = url2;
    }
    public void setNormalAnimation(ReplacementAnimation normalAnimation) {
        this.normalAnimation = normalAnimation;
        beforeUpdate();
    }

    public void setHoverAnimation(ReplacementAnimation hoverAnimation) {
        this.hoverAnimation = hoverAnimation;
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
}
