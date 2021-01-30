package me.wuxie.wakeshow.wakeshow.network.client_in;

import io.netty.buffer.ByteBuf;
import me.wuxie.wakeshow.wakeshow.WakeShow;
import me.wuxie.wakeshow.wakeshow.api.WuxieAPI;
import me.wuxie.wakeshow.wakeshow.network.InPacket;
import me.wuxie.wakeshow.wakeshow.network.PacketHandler;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageHook;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageNetty;
import org.bukkit.entity.Player;

public class MessageRequestHook extends InPacket {
    public MessageRequestHook(ByteBuf buf) {
        super(8, buf);
    }
    @Override
    public void handlePacket(Player player) {
        //if(!PacketHandler.isUseNettyServer()){
            PacketHandler.sendToPlayer(player,new MessageHook(WuxieAPI.hookData));
        //}else {
            //player.sendPluginMessage(WakeShow.getPlugin(),PacketHandler.servercannel,new MessageNetty().getPacketBuffer().array());
        //}
    }
}
