package me.wuxie.wakeshow.wakeshow.api.event;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * 玩家点击一个GUI组件后事件
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class PlayerPostClickComponentEvent extends PlayerEvent {
    /** 对应GUI */
    @Getter
    private final WxScreen screen;
    /** 对应组件 */
    @Getter
    private final Component component;
    /** 0左键1右键2中键 */
    @Getter
    private final int mouseButton;
    public PlayerPostClickComponentEvent(Player player, WxScreen screen, Component component, int mouseButton) {
        super(player);
        this.screen = screen;
        this.component = component;
        this.mouseButton = mouseButton;
    }
    private static final HandlerList handlers = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
