package me.wuxie.wakeshow.wakeshow.api.event;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * 玩家点击一个GUI组件前事件
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class PlayerPreClickComponentEvent extends PlayerEvent implements Cancellable {
    @Getter private WxScreen screen;
    @Getter private Component component;
    @Getter private int mouseButton;
    public PlayerPreClickComponentEvent(Player player, WxScreen screen, Component component, int mouseButton) {
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
    private boolean cancelled = false;
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
