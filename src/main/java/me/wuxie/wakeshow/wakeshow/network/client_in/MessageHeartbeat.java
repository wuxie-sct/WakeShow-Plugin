package me.wuxie.wakeshow.wakeshow.network.client_in;

import io.netty.buffer.ByteBuf;
import me.wuxie.wakeshow.wakeshow.WakeShow;
import me.wuxie.wakeshow.wakeshow.api.WuxieAPI;
import me.wuxie.wakeshow.wakeshow.api.event.WClientProofreadingEvent;
import me.wuxie.wakeshow.wakeshow.network.InPacket;
import me.wuxie.wakeshow.wakeshow.network.PacketHandler;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

@Deprecated
public class MessageHeartbeat extends InPacket {
    private int heartbeatKey;
    public MessageHeartbeat(ByteBuf buf) {
        super(9, buf);
        heartbeatKey = buf.readInt();
    }
    // 1s 内只会触发1次WClientProofreadingEvent
    //private static final Set<UUID> uuids = new HashSet<>();

    @Override
    public void handlePacket(Player player) {

    }

    /*@Override
    public void handlePacket(Player player) {
        PacketHandler.sendToPlayer(player,new me.wuxie.wakeshow.wakeshow.network.server_out.MessageHeartbeat());
        // 初始心跳包
        if(heartbeatKey==-1){
            if(!uuids.contains(player.getUniqueId())) {
                WClientProofreadingEvent event = new WClientProofreadingEvent(player);
                Bukkit.getPluginManager().callEvent(event);
                uuids.add(player.getUniqueId());
                Bukkit.getScheduler().runTaskLater(WakeShow.getPlugin(),()-> uuids.remove(player.getUniqueId()),20);
            }
        // 异常心跳包(玩家bc服切换/服务器reload)
        }else if(heartbeatKey != WakeShow.getHeartbeatKey()){
            // 重新发送hook包
            if(!uuids.contains(player.getUniqueId())) {
                PacketHandler.sendToPlayer(player, new MessageHook(WuxieAPI.hookData));
                WClientProofreadingEvent event = new WClientProofreadingEvent(player);
                Bukkit.getPluginManager().callEvent(event);
                uuids.add(player.getUniqueId());
                Bukkit.getScheduler().runTaskLater(WakeShow.getPlugin(),()-> uuids.remove(player.getUniqueId()),20);
            }
        }
    }*/
}
