package me.wuxie.wakeshow.wakeshow.network.client_in;

import io.netty.buffer.ByteBuf;
import me.wuxie.wakeshow.wakeshow.WakeShow;
import me.wuxie.wakeshow.wakeshow.api.WuxieAPI;
import me.wuxie.wakeshow.wakeshow.api.event.PlayerPostClickInventorySlotEvent;
import me.wuxie.wakeshow.wakeshow.api.event.PlayerPreClickInventorySlotEvent;
import me.wuxie.wakeshow.wakeshow.network.InPacket;
import me.wuxie.wakeshow.wakeshow.ui.OpenedGui;

import me.wuxie.wakeshow.wakeshow.ui.WInventoryScreen;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;
import me.wuxie.wakeshow.wakeshow.ui.inventory.InvSlotProxyScreen;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MessageClickInventorySlot extends InPacket {
    public MessageClickInventorySlot(ByteBuf buf) {
        super(3, buf);
    }

    // 还原
    protected void backClick(Player player,int slot,WxScreen screen){
        player.getInventory().setItem(slot,player.getInventory().getItem(slot));
        screen.setCursor(screen.getCursor());
        // 处理完，更新客户端
        WuxieAPI.getOpenedGui(player).update();
    }

    @Override
    public void handlePacket(Player player) {
        int mouseButtonId = getPacketBuffer().readByte();
        int slotIndex = getPacketBuffer().readInt();
        boolean canDrag = getPacketBuffer().readBoolean();
        OpenedGui openedGui = WuxieAPI.getOpenedGui(player);
        if(openedGui==null) {
            return;
        }
        WxScreen screen = openedGui.getScreen();
        if(screen instanceof InvSlotProxyScreen){
            PlayerPreClickInventorySlotEvent event =
                    new PlayerPreClickInventorySlotEvent(player,(InvSlotProxyScreen)screen,mouseButtonId,slotIndex);
            Bukkit.getPluginManager().callEvent(event);
            if(event.isCancelled()){
                if(canDrag){
                    backClick(player,slotIndex,screen);
                }
                return;
            }
            if(canDrag) {
                ItemStack cursor = screen.getCursor();
                ItemStack itemStack = player.getInventory().getItem(slotIndex);
                itemStack = itemStack != null ? itemStack.clone() : null;
                switch (mouseButtonId) {
                    case 0: {
                        // 光标有物品
                        if (cursor != null && !cursor.getType().equals(Material.AIR)) {
                            // 物品不同，交换
                            if (!cursor.isSimilar(itemStack)) {
                                screen.setCursor(itemStack);
                                player.getInventory().setItem(slotIndex, cursor);
                                //物品相同，合并
                            } else {
                                int cursorAmount = cursor.getAmount();
                                // isSimilar物品为空返回false
                                assert itemStack != null;
                                int slotAmount = itemStack.getAmount();
                                int all = cursorAmount + slotAmount;
                                int maxSize = itemStack.getMaxStackSize();
                                if (all > maxSize) {
                                    itemStack.setAmount(maxSize);
                                    player.getInventory().setItem(slotIndex, itemStack);
                                    cursor.setAmount(all - maxSize);
                                    screen.setCursor(cursor);
                                } else {
                                    itemStack.setAmount(all);
                                    player.getInventory().setItem(slotIndex, itemStack);
                                    screen.setCursor(null);
                                }
                            }
                            // 光标没物品
                        } else {
                            screen.setCursor(itemStack);
                            player.getInventory().setItem(slotIndex, null);
                        }
                    }
                    break;
                    // 右键
                    case 1: {
                        // 如果光标有物品
                        if (cursor != null && !cursor.getType().equals(Material.AIR)) {
                            // 如果槽位有物品
                            if (itemStack != null && !itemStack.getType().equals(Material.AIR)) {
                                // 如果两个相同,光标减一，槽位加一
                                if (itemStack.isSimilar(cursor)) {
                                    int cursorAmount = cursor.getAmount();
                                    int slotAmount = itemStack.getAmount();
                                    int maxSize = itemStack.getMaxStackSize();
                                    if (slotAmount + 1 < maxSize) {
                                        itemStack.setAmount(slotAmount + 1);
                                        player.getInventory().setItem(slotIndex, itemStack);
                                        if (cursorAmount > 1) cursor.setAmount(cursorAmount - 1);
                                        else cursor = null;
                                        screen.setCursor(cursor);
                                    }
                                    // 否则交换
                                } else {
                                    screen.setCursor(itemStack);
                                    player.getInventory().setItem(slotIndex, cursor);
                                }
                                // 如果槽位没有物品,光标减一，槽位加一光标物品
                            } else {
                                int cursorAmount = cursor.getAmount();
                                if (cursorAmount > 1) {
                                    cursor.setAmount(cursorAmount - 1);
                                    screen.setCursor(cursor.clone());
                                    cursor.setAmount(1);
                                    player.getInventory().setItem(slotIndex, cursor);
                                    // 如果光标只有一个物品
                                } else {
                                    player.getInventory().setItem(slotIndex, cursor);
                                    screen.setCursor(null);
                                }
                            }
                            // 如果光标没有物品，槽位物品减半，光标物品得半
                        } else if (itemStack != null && !itemStack.getType().equals(Material.AIR)) {
                            int slotAmount = itemStack.getAmount();
                            int half = (int) Math.round(slotAmount / 2.0);
                            itemStack.setAmount(half);
                            screen.setCursor(itemStack.clone());
                            itemStack.setAmount(slotAmount - half);
                            player.getInventory().setItem(slotIndex, itemStack);
                        }
                    }
                    break;
                    // 鼠标中键,不在客户端预处理// 克隆一组
                    case 2: {
                        if (player.isOp()) {
                            if (itemStack != null && !itemStack.getType().equals(Material.AIR)) {
                                itemStack.setAmount(itemStack.getMaxStackSize());
                                screen.setCursor(itemStack);
                                // 处理完，更新客户端
                                WuxieAPI.getOpenedGui(player).update();
                            }
                        }
                    }
                    break;
                }
                PlayerPostClickInventorySlotEvent postEvent =
                        new PlayerPostClickInventorySlotEvent(player, (InvSlotProxyScreen) screen, mouseButtonId, slotIndex);
                Bukkit.getPluginManager().callEvent(postEvent);
            }
        }
    }
}
