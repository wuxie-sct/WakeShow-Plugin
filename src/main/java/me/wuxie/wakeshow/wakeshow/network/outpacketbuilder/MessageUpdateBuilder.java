package me.wuxie.wakeshow.wakeshow.network.outpacketbuilder;

import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageUpdate;
import me.wuxie.wakeshow.wakeshow.network.OutPacketBuilder;

public class MessageUpdateBuilder implements OutPacketBuilder<MessageUpdate> {
    public static final MessageUpdateBuilder instance = new MessageUpdateBuilder();
    private MessageUpdateBuilder(){}
    @Override
    public byte[] builder(MessageUpdate outPacket) {
        PacketBuffer buffer = outPacket.getPacketBuffer();
        int writeIndex = buffer.writerIndex();
        byte[] array = new byte[writeIndex];
        buffer.readBytes(array);
        return array;
    }
}
