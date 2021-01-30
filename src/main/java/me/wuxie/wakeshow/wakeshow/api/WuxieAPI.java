package me.wuxie.wakeshow.wakeshow.api;

import me.wuxie.wakeshow.wakeshow.network.PacketHandler;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageGuiItemStack;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.ui.ContainerOwner;
import me.wuxie.wakeshow.wakeshow.ui.OpenedGui;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;
import me.wuxie.wakeshow.wakeshow.WakeShow;
import me.wuxie.wakeshow.wakeshow.api.event.PlayerOpenScreenEvent;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageOpenGui;
import me.wuxie.wakeshow.wakeshow.ui.component.WSlot;
import me.wuxie.wakeshow.wakeshow.ui.hudcomponent.HudComponent;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
/**
 * API
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class WuxieAPI {
    /**
     * 为玩家打开一个GUI
     *
     * @param player 玩家
     * @param screen gui
     */
    public static void openGui(Player player, WxScreen screen){
        screen.getUpdateSlots().clear();
        // gui 打开之前
        PlayerOpenScreenEvent playerOpenScreenEvent = new PlayerOpenScreenEvent(player,screen);
        Bukkit.getPluginManager().callEvent(playerOpenScreenEvent);
        if(playerOpenScreenEvent.isCancelled())return;
        screen = playerOpenScreenEvent.getScreen();
        PacketHandler.sendToPlayer(player,new MessageOpenGui(screen));
        List<ContainerOwner> containerOwnerList = new ArrayList<>();
        containerOwnerList.add(screen);
        Map<String,ItemStack> guiItemStacks = new HashMap<>();
        while (containerOwnerList.size()>0){
            ContainerOwner owner = containerOwnerList.get(0);
            for (Component component: owner.getContainer().getComponentMap().values()){
                if(component instanceof ContainerOwner){
                    containerOwnerList.add((ContainerOwner) component);
                }else if(component instanceof WSlot){
                    ItemStack itemStack = ((WSlot) component).getItemStack();
                    if(itemStack!=null&&!itemStack.getType().equals(Material.AIR)){
                        guiItemStacks.put(component.getPath(),itemStack);
                    }
                }
            }
            containerOwnerList.remove(0);
        }
        for (Map.Entry<String,ItemStack> mp: guiItemStacks.entrySet()){
            PacketHandler.sendToPlayer(player,new MessageGuiItemStack(mp.getKey(),guiItemStacks.size(),mp.getValue()));
        }
        if(guiItemStacks.size() == 0){
            PacketHandler.sendToPlayer(player,new MessageGuiItemStack(screen.getId()+".",0,new ItemStack(Material.APPLE)));
        }
        WakeShow.getOpenedGuiManager().putOpenGui(player,screen);
    }

    /**
     * 关闭玩家的gui
     * @param player 玩家
     */
    public static void closeGui(Player player){
        OpenedGui openedGui = getOpenedGui(player);
        if(openedGui!=null){
            openedGui.close_Server();
        }
    }

    /**
     * 更新玩家的gui
     * @param player 玩家
     */
    public static void updateGui(Player player){
        OpenedGui openedGui = getOpenedGui(player);
        if(openedGui!=null){
            openedGui.update();
        }
    }

    /**
     * 给玩家添加HUD组件
     * @param player 玩家
     * @param hudComponent HUD组件
     */
    public static void addHudComponent(Player player, HudComponent hudComponent){
        if(hudComponent.getPlayer().equals(player)){
            WakeShow.getPlayerHudComponentManager().addHudComponent(player,hudComponent);
        }
    }

    /**
     * 移除玩家屏幕上的HUD组件
     * @param player 玩家
     * @param id 需要移除的HUD组件的ID
     */
    public static void removeHudComponent(Player player, String id){
        WakeShow.getPlayerHudComponentManager().removeHudComponent(player,id);
    }

    /**
     * 获得玩家屏幕上的HUD组件实例
     * @param player 玩家
     * @param id HUD组件的ID
     * @return HudComponent实例对象，有可能为空
     */
    public static HudComponent getPlayerHudComponent(Player player,String id){
        return WakeShow.getPlayerHudComponentManager().getPlayerHudComponent(player,id);
    }

    /**
     * 设置物品的冷却
     * @param player 玩家
     * @param itemStack 物品
     * @param tick 刻 20 = 1s
     */
    public static void setItemStackCooldown(Player player, ItemStack itemStack,int tick){
        WakeShow.getCooldownManager().setCooldown(player,itemStack,tick);
    }
    /**
     * 获得物品的冷却
     * @param player 玩家
     * @param itemStack 物品
     * @return  tick 刻 20 = 1s
     */
    public static int getItemStackCooldown(Player player, ItemStack itemStack){
        return WakeShow.getCooldownManager().getCooldown(player,itemStack);
    }

    /**
     * 判断物品是否冷却完毕
     * @param player 玩家
     * @param itemStack 物品
     * @return 物品是否冷却完毕
     */
    public static boolean isCoolingComplete(Player player, ItemStack itemStack){
        return WakeShow.getCooldownManager().isCoolingComplete(player,itemStack);
    }

    /**
     * 获得玩家打开的GUI
     * 通过OpenedGui实例，调用getScreen可以获得WxScreen对象
     * @param player 玩家
     *
     * @return 玩家打开的GUI，有可能为空
     */
    public static OpenedGui getOpenedGui(Player player){
        return WakeShow.getOpenedGuiManager().getOpenedGui(player);
    }
    /** 在此列表中存入网络图片的URL链接，玩家在进服时，客户端会预先下载 */
    public static List<String> urlImages = new ArrayList<>();
    /**
     * @param  url 链接
     * 往 urlImages 中添加图片
     * */
    public static void registerURLImage(String url){
        urlImages.add(url);
    }
    /** 自用 */
    public static Set<String> hookData = new HashSet<>();
     /** 自用 */
    public static void addHookData(String data){
        hookData.add(data);
    }
}
