package me.wuxie.wakeshow.wakeshow.api.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
/**
 * 玩家物品遗留事件
 * 在玩家关闭界面，鼠标上如果有物品，物品会丢出并触发PlayerDropItemEvent事件
 * 如果PlayerDropItemEvent事件被取消，则这个物品会进入背包
 * 如果背包满了，则会触发此事件
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class PlayerItemLeftoverEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    @Getter
    /** 遗留的物品 */
    private Map<Integer, ItemStack> leftoverMap;

    public PlayerItemLeftoverEvent(Player player, Map<Integer, ItemStack> leftoverMap) {
        super(player);
        this.leftoverMap = leftoverMap;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
