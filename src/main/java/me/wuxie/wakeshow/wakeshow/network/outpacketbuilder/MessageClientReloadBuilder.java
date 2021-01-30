package me.wuxie.wakeshow.wakeshow.network.outpacketbuilder;

import me.wuxie.wakeshow.wakeshow.network.OutPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageClientReload;

public class MessageClientReloadBuilder implements OutPacketBuilder<MessageClientReload> {
    public static final MessageClientReloadBuilder instance = new MessageClientReloadBuilder();
    private MessageClientReloadBuilder(){}
    @Override
    public byte[] builder(MessageClientReload outPacket) {
        PacketBuffer buffer = outPacket.getPacketBuffer();
        int writeIndex = buffer.writerIndex();
        byte[] array = new byte[writeIndex];
        buffer.readBytes(array);
        return array;
    }
}
