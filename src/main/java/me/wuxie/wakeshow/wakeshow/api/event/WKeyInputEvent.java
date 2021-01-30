package me.wuxie.wakeshow.wakeshow.api.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * 玩家按下按键事件
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class WKeyInputEvent extends PlayerEvent {
    /**
     * PRESS 按下
     * RELEASE 弹起
     */
    @Getter
    private KeyType keyType;
    @Getter
    int KeyId;
    public WKeyInputEvent(Player player,KeyType keyType,int KeyId) {
        super(player);
        this.keyType = keyType;
        this.KeyId = KeyId;
    }
    private static final HandlerList handlers = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public enum KeyType{
        PRESS,
        RELEASE;
    }
}
