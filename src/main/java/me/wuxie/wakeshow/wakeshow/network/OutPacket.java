package me.wuxie.wakeshow.wakeshow.network;

import io.netty.buffer.Unpooled;
import lombok.Getter;

public abstract class OutPacket {
    @Getter
    private int packetId;
    @Getter
    private PacketBuffer packetBuffer;

    private void writePacketId(int id) {
        this.packetBuffer = new PacketBuffer(Unpooled.buffer());
        /*if(!(this instanceof MessageNetty) &&PacketHandler.isUseNettyServer()){
            packetBuffer.writeString("wakeshow");
        }*/
        packetBuffer.writeByte(id);
        this.packetId = id;
    }

    public OutPacket(int id) {
        this.writePacketId(id);
    }
}
