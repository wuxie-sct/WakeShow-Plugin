package me.wuxie.wakeshow.wakeshow.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;

import me.wuxie.wakeshow.wakeshow.network.inpacketbuilder.*;
import me.wuxie.wakeshow.wakeshow.network.inpacketbuilder.MessageCloseGuiBuilder;


import me.wuxie.wakeshow.wakeshow.network.outpacketbuilder.*;
import me.wuxie.wakeshow.wakeshow.WakeShow;

import me.wuxie.wakeshow.wakeshow.util.messaging.MessageSend;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.HashMap;
import java.util.Map;

public class PacketHandler implements PluginMessageListener {
    public static final String clientcannel = "WakeShow_client";
    public static final String servercannel = "WakeShow_server";
    //@Getter
    //private static boolean useNettyServer = false;
    @Getter
    private static final Map<Integer,InPacketBuilder> inPacketBuilderMap = new HashMap<>();
    @Getter
    private static final Map<Integer,OutPacketBuilder> outPacketBuilderMap = new HashMap<>();

    static {
        regInPacketBuilder(0, MessageClickComponentBuilder.instance);
        regInPacketBuilder(1, MessageCloseGuiBuilder.instance);
        regInPacketBuilder(2, MessageTextFieldInputBuilder.instance);
        regInPacketBuilder(3, MessageClickInventorySlotBuilder.instance);
        regInPacketBuilder(4, MessageWindowSizeChangeBuilder.instance);
        regInPacketBuilder(5, MessageClickHudComponentBuilder.instance);
        regInPacketBuilder(7, MessageKeyInputBuilder.instance);
        regInPacketBuilder(8, MessageRequestHookBuilder.instance);
        //regInPacketBuilder(9, MessageHeartbeatBuilder.instance);
        // 跳过 9
        // 跳过 10
        //regInPacketBuilder(11, MessageNettyLoginBuilder.instance);
        // 跳过 11

        regOutPacketBuilder(0, MessageOpenGuiBuilder.instance);
        regOutPacketBuilder(1, me.wuxie.wakeshow.wakeshow.network.outpacketbuilder.MessageCloseGuiBuilder.instance);
        regOutPacketBuilder(2, MessageUpdateBuilder.instance);
        regOutPacketBuilder(3, MessageComponentReturnDataBuilder.instance);
        regOutPacketBuilder(4, MessageUpdateSlotBuilder.instance);
        regOutPacketBuilder(5, MessageDownloadImageBuilder.instance);
        regOutPacketBuilder(6, MessageHookBuilder.instance);
        regOutPacketBuilder(7, MessageAddHudComponentBuilder.instance);
        regOutPacketBuilder(8, MessageRemoveHudComponentBuilder.instance);
        regOutPacketBuilder(9, MessageItemCooldownBuilder.instance);
        regOutPacketBuilder(10, MessageEditHookDataBuilder.instance);
        //regOutPacketBuilder(11, me.wuxie.wakeshow.wakeshow.network.outpacketbuilder.MessageHeartbeatBuilder.instance);
        // 跳过11
        regOutPacketBuilder(12, MessageGuiItemStackBuilder.instance);
        // 跳过13
        // 跳过14
        regOutPacketBuilder(15, MessageDecryptImageBuilder.instance);
        regOutPacketBuilder(16, MessageClientReloadBuilder.instance);
        //regOutPacketBuilder(17, MessageNettyBuilder.instance);
        // 跳过17
        regOutPacketBuilder(18, MessageUpdateCursorBuilder.instance);
        regOutPacketBuilder(19, MessageUnpackingBuilder.instance);

        /*if(WakeShow.getPlugin().getConfig().getBoolean("nettyServer.use",false)){
            ServerThread.startServerThread(WakeShow.getPlugin().getConfig().getInt("nettyServer.port"));
            useNettyServer = true;
        }*/
    }
    /**
     * A method that will be thrown when a PluginMessageSource sends a plugin
     * message on a registered channel.
     *
     * @param channel Channel that the message was sent through.
     * @param player  Source of the message.
     * @param message The raw message that was sent.
     */
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        ByteBuf buf = Unpooled.wrappedBuffer(message);
        int packetId = buf.readByte();
        InPacketBuilder builder = inPacketBuilderMap.get(packetId);
        if(builder!=null){
            InPacket inPacket = builder.builder(buf);
            inPacket.handlePacket(player);
        }
    }

    public static void sendToPlayer(Player player,String cannel,byte[] bytes){
        //if(useNettyServer){
            //ServerThread.sendPacket(player.getUniqueId(),bytes);
        //}else {
            MessageSend.send(player, cannel, bytes);
        //}
    }

    public static void sendToPlayer(Player player,byte[] bytes){
        //if(useNettyServer){
            //ServerThread.sendPacket(player.getUniqueId(),bytes);
        //}else {
            MessageSend.send(player, servercannel, bytes);
            //player.sendPluginMessage(WakeShow.getPlugin(), servercannel, bytes);
        //}
    }

    public static void sendToPlayer(Player player,OutPacket outPacket){
        //if(useNettyServer){
            //ServerThread.sendPacket(player.getUniqueId(),getOutPacketData(outPacket));
        //}else {
            MessageSend.send(player, servercannel, getOutPacketData(outPacket));
        //}
        //System.out.println(outPacket.getClass().getSimpleName());
    }

    public static void sendToPlayer(Player player,String cannel,OutPacket outPacket){
        //if(useNettyServer){
            //ServerThread.sendPacket(player.getUniqueId(),getOutPacketData(outPacket));
        //}else {
            MessageSend.send(player, cannel, getOutPacketData(outPacket));
        //}
    }

    public static void regInPacketBuilder(int packetId,InPacketBuilder builder){
        inPacketBuilderMap.put(packetId,builder);
    }

    public static void regOutPacketBuilder(int packetId,OutPacketBuilder builder){
        outPacketBuilderMap.put(packetId,builder);
    }

    public static byte[] getOutPacketData(OutPacket outPacket){
        return outPacketBuilderMap.get(outPacket.getPacketId()).builder(outPacket);
    }
}
