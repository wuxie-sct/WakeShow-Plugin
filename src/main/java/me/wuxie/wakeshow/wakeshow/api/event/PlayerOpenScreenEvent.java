package me.wuxie.wakeshow.wakeshow.api.event;

import lombok.Getter;
import lombok.Setter;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
/**
 * 玩家打开GUI事件
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class PlayerOpenScreenEvent extends PlayerEvent implements Cancellable{
    @Getter
    @Setter
    private WxScreen screen;

    public PlayerOpenScreenEvent(Player player, WxScreen screen) {
        super(player);
        this.screen = screen;
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
