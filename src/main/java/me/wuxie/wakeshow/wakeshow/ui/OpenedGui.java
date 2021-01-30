package me.wuxie.wakeshow.wakeshow.ui;


import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.WakeShow;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageUpdate;
import me.wuxie.wakeshow.wakeshow.api.event.PlayerCloseScreenEvent;
import me.wuxie.wakeshow.wakeshow.api.event.ScreenPreUpdateEvent;
import me.wuxie.wakeshow.wakeshow.network.PacketHandler;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageCloseGui;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageUpdateCursor;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageUpdateSlot;
import me.wuxie.wakeshow.wakeshow.ui.component.WSlot;
import me.wuxie.wakeshow.wakeshow.util.JsonUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * 打开的GUI
 */
public class OpenedGui {
    /**
     * 对应的WxScreen实例
     */
    @Getter
    private WxScreen screen;
    /**
     * 对应玩家
     */
    @Getter
    private Player player;
    /**
     * 什么时候打开的
     */
    @Getter
    private long openTime;

    public OpenedGui(Player player, WxScreen screen){
        this.screen = screen;
        this.player = player;
        screen.opened = true;
        screen.player = player;
        this.openTime = System.currentTimeMillis();
    }

    /**
     * 更新这个界面
     */
    public void update(){
        //更新前
        ScreenPreUpdateEvent preUpdateEvent = new ScreenPreUpdateEvent(player,screen);
        Bukkit.getPluginManager().callEvent(preUpdateEvent);
        if(preUpdateEvent.isCancelled())return;
        List<ContainerOwner> ownerList = new ArrayList<>();
        ownerList.add(screen);
        while (ownerList.size()>0){
            ContainerOwner owner = ownerList.get(0);
            String updateData = JsonUtil.updateComponentToJson(owner, owner.getContainer().getUpdateList());
            sendUpdateMessage(updateData);
            for (Component component: owner.getContainer().getUpdateList()){
                if(component instanceof ContainerOwner){
                    ownerList.add((ContainerOwner) component);
                    if(component.getUpdateType() == Component.UpdateType.ADD){
                        for (Component c: ((ContainerOwner) component).getContainer().getComponentMap().values()){
                            if(c instanceof WSlot){
                                c.setUpdateType(Component.UpdateType.UPDATE);
                                screen.getUpdateSlots().add((WSlot) c);
                            }
                        }
                    }
                }
                component.setUpdateType(Component.UpdateType.NORMAL);
            }
            owner.getContainer().afterUpdate();
            ownerList.remove(0);
        }
        // 发送槽位的update
        for (WSlot slot : screen.getUpdateSlots()){
            if(!slot.getUpdateType().equals(Component.UpdateType.REMOVE)) {
                PacketHandler.sendToPlayer(player, new MessageUpdateSlot(slot));
            }
        }
        Bukkit.getScheduler().runTaskLater(WakeShow.getPlugin(),()-> PacketHandler.sendToPlayer(player,PacketHandler.servercannel,new MessageUpdateCursor(screen.getId(),screen.getCursor())),3);
        //PacketHandler.sendToPlayer(player, );
        screen.getUpdateSlots().clear();
    }

    private void sendUpdateMessage(String json){
        PacketHandler.sendToPlayer(player,new MessageUpdate(json));
    }

    /**
     * 服务器发送关闭gui数据
     */
    public void close_Server(){
        PacketHandler.sendToPlayer(player,new MessageCloseGui(screen));
        close_Client();
    }

    /**
     * 客户端关闭gui(客户端传送的关闭界面数据包也会执行)
     */
    public void close_Client(){
        screen.opened = false;
        WakeShow.getOpenedGuiManager().getOpenedGuiMap().remove(player.getUniqueId());
        // 传递事件
        PlayerCloseScreenEvent playerCloseScreenEvent = new PlayerCloseScreenEvent(player,screen);
        Bukkit.getPluginManager().callEvent(playerCloseScreenEvent);
    }

    /**
     *  管理器循环
     */
    public void tick(){
    }
}
