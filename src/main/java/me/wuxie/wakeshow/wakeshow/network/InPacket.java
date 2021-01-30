package me.wuxie.wakeshow.wakeshow.network;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class InPacket {
    @Getter
    private int packetId;
    @Getter
    @Setter
    protected PacketBuffer packetBuffer;

    protected PacketBuffer getData() {
        return this.packetBuffer;
    }
    public abstract void handlePacket(Player player);

    public InPacket(int id, ByteBuf buf) {
        packetId = id;
        packetBuffer = new PacketBuffer(buf);
    }

    @Deprecated
    public void handlePacket(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if(player == null){
            //System.out.println("玩家为空! "+uuid);
        }else handlePacket(player);
    }
}