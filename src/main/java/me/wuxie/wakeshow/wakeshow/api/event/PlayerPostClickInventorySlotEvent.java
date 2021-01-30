package me.wuxie.wakeshow.wakeshow.api.event;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.api.WuxieAPI;
import me.wuxie.wakeshow.wakeshow.ui.inventory.InvSlotProxyScreen;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

/**
 * 玩家点击一个WInventoryScreen中的原版槽位后事件
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class PlayerPostClickInventorySlotEvent  extends PlayerEvent {
    @Getter
    private final InvSlotProxyScreen screen;
    @Getter
    private final int mouseButton;
    @Getter
    private int slotId;
    public PlayerPostClickInventorySlotEvent(Player player, InvSlotProxyScreen screen, int mouseButton, int slotId) {
        super(player);
        this.screen = screen;
        this.mouseButton = mouseButton;
        this.slotId = slotId;
    }
    private static final HandlerList handlers = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public ItemStack getClickItem(){
        return player.getInventory().getItem(slotId);
    }

    public ItemStack getCursor(){
        return WuxieAPI.getOpenedGui(player).getScreen().getCursor();
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
