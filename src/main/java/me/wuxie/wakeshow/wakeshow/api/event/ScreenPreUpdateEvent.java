package me.wuxie.wakeshow.wakeshow.api.event;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
/**
 * 玩家GUI更新事件
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class ScreenPreUpdateEvent extends PlayerEvent implements Cancellable {
    @Getter
    private WxScreen screen;
    public ScreenPreUpdateEvent(Player player, WxScreen screen) {
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
