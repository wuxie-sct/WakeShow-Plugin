package me.wuxie.wakeshow.wakeshow.network.client_in;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.api.WuxieAPI;
import me.wuxie.wakeshow.wakeshow.network.InPacket;
import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import me.wuxie.wakeshow.wakeshow.network.PacketHandler;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageDecryptImage;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageDownloadImage;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageHook;
import org.bukkit.entity.Player;

import java.util.UUID;
@Deprecated
public class MessageNettyLogin extends InPacket {
    @Getter
    private final UUID uuid;
    public MessageNettyLogin(ByteBuf buf) {
        super(11, buf);
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        uuid = UUID.fromString(packetBuffer.readString());
    }

    @Override
    public void handlePacket(Player player) {
        PacketHandler.sendToPlayer(player,new MessageHook(WuxieAPI.hookData));
        // 预下载网络图片
        if(WuxieAPI.urlImages.size()>0){
            PacketHandler.sendToPlayer(player,new MessageDownloadImage(WuxieAPI.urlImages));
        }
        PacketHandler.sendToPlayer(player,new MessageDecryptImage());
    }
}
