package me.wuxie.wakeshow.wakeshow.util.messaging;

import io.netty.buffer.Unpooled;
import me.wuxie.wakeshow.wakeshow.WakeShow;
import me.wuxie.wakeshow.wakeshow.network.PacketHandler;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageUnpacking;
import me.wuxie.wakeshow.wakeshow.network.unpacking.PacketData;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.ChannelNameTooLongException;
import org.bukkit.plugin.messaging.ChannelNotRegisteredException;
import org.bukkit.plugin.messaging.MessageTooLargeException;
import org.bukkit.plugin.messaging.Messenger;

import java.util.List;
import java.util.Set;

public class MessageSend {
    public static void send(Player player,String channel,byte[] message){
        //System.out.println(message.length);
        if(message.length>32767){
            PacketData packetData = new PacketData(message);
            List<byte[]> byteList = packetData.getByteList();
            for (byte[] bytes:byteList){
                PacketHandler.sendToPlayer(player,channel,new MessageUnpacking(bytes));
            }
        } else {
            player.sendPluginMessage(WakeShow.getPlugin(),channel,message);
            /*validatePluginMessage(Bukkit.getMessenger(), WakeShow.getPlugin(),channel,message);
            CraftPlayer craftPlayer = (CraftPlayer) player;
            Set<String> channels = craftPlayer.getListeningPluginChannels();
            if(channels.contains(channel)) {
                PlayerConnection playerConnection = craftPlayer.getHandle().playerConnection;
                if(playerConnection!=null) {
                    PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload(channel, new PacketDataSerializer(Unpooled.wrappedBuffer(message)));
                    craftPlayer.getHandle().playerConnection.sendPacket(packet);
                }
            }*/
        }
    }

    /**
     * Validates a Plugin Channel name.
     * 验证插件通道名称。
     *
     * @param channel 要验证的通道名称。
     */
    /*private static void validateChannel(String channel) {
        if (channel == null) {
            throw new IllegalArgumentException("Channel cannot be null");
        }
        if (channel.length() > Messenger.MAX_CHANNEL_SIZE) {
            throw new ChannelNameTooLongException(channel);
        }
    }

    /**
     * Validates the input of a Plugin Message, ensuring the arguments are all
     * valid.
     * 验证插件消息的输入，确保所有参数都有效。
     *
     * @param messenger 用于验证的Messenger。
     * @param source 消息的源插件。
     * @param channel 用于发送消息的插件通道。
     * @param message 要发送的原始消息负载。
     * @throws IllegalArgumentException 如果源插件是disabled，则引发。
     * @throws IllegalArgumentException 如果源、通道或消息为空，则引发。
     * @throws MessageTooLargeException 如果消息太大则抛出。
     * @throws ChannelNameTooLongException 如果频道名称太长，则引发。
     * @throws ChannelNotRegisteredException 如果没有为此插件注册通道，则引发。
     */
    /*private static void validatePluginMessage(Messenger messenger, Plugin source, String channel, byte[] message) {
        if (messenger == null) {
            throw new IllegalArgumentException("Messenger cannot be null");
        }
        if (source == null) {
            throw new IllegalArgumentException("Plugin source cannot be null");
        }
        if (!source.isEnabled()) {
            throw new IllegalArgumentException("Plugin must be enabled to send messages");
        }
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        if (!messenger.isOutgoingChannelRegistered(source, channel)) {
            throw new ChannelNotRegisteredException(channel);
        }
        if (message.length > Messenger.MAX_MESSAGE_SIZE) {
            throw new MessageTooLargeException(message);
        }
        validateChannel(channel);
    }*/
}
