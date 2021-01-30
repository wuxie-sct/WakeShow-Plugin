package me.wuxie.wakeshow.wakeshow.api.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * 玩家调整窗口大小事件
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class PlayerWindowChangeEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    /**
     * 调整后宽度
     * */
    @Getter
    private final int width;
    /**
     * 调整后高度
     * */
    @Getter
    private final int height;
    public PlayerWindowChangeEvent(Player player,int width,int height) {
        super(player);
        this.width = width;
        this.height = height;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
