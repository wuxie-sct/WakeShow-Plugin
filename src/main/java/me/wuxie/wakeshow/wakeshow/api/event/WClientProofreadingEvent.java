package me.wuxie.wakeshow.wakeshow.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * 服务器与客户端校对事件
 * 成功与客户端挂钩后，进行心跳包的校对
 * 在这个事件后面添加hud和hook信息最合适
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class WClientProofreadingEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    public WClientProofreadingEvent(Player player) {
        super(player);
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
