package me.wuxie.wakeshow.wakeshow.api.event;

import lombok.Getter;
import lombok.Setter;
import me.wuxie.wakeshow.wakeshow.api.WuxieAPI;
import me.wuxie.wakeshow.wakeshow.ui.inventory.InvSlotProxyScreen;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

/**
 * 玩家点击一个WInventoryScreen中的原版槽位前事件
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class PlayerPreClickInventorySlotEvent extends PlayerEvent implements Cancellable {
    @Getter
    private InvSlotProxyScreen screen;
    @Getter
    private int mouseButton;
    @Getter
    private int slotId;
    public PlayerPreClickInventorySlotEvent(Player player, InvSlotProxyScreen screen, int mouseButton, int slotId) {
        super(player);
        this.screen = screen;
        this.mouseButton = mouseButton;
        this.slotId = slotId;
    }
    @Getter
    @Setter
    private boolean cancelled = false;

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
