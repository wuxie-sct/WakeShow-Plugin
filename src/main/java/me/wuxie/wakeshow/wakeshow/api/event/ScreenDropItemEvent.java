package me.wuxie.wakeshow.wakeshow.api.event;

import lombok.Getter;
import lombok.Setter;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

/**
 * 玩家通过WxScreen丢出物品事件
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class ScreenDropItemEvent extends PlayerEvent implements Cancellable {
    @Getter
    @Setter
    private WxScreen screen;
    /**
     * 丢出的物品
     */
    @Getter
    @Setter
    private ItemStack itemStack;

    public ScreenDropItemEvent(Player player, WxScreen screen,ItemStack itemStack) {
        super(player);
        this.screen = screen;
        this.itemStack = itemStack;
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
