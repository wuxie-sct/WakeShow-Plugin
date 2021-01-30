package me.wuxie.wakeshow.wakeshow.api.event;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
/**
 * 玩家关闭gui事件
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class PlayerCloseScreenEvent extends PlayerEvent {
    @Getter
    private WxScreen screen;

    public PlayerCloseScreenEvent(Player player, WxScreen screen) {
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
}
