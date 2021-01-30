package me.wuxie.wakeshow.wakeshow.network.outpacketbuilder;

import me.wuxie.wakeshow.wakeshow.network.OutPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageUpdateCursor;

public class MessageUpdateCursorBuilder implements OutPacketBuilder<MessageUpdateCursor> {
    public static final MessageUpdateCursorBuilder instance = new MessageUpdateCursorBuilder();
    private MessageUpdateCursorBuilder(){}
    @Override
    public byte[] builder(MessageUpdateCursor outPacket) {
        PacketBuffer buffer = outPacket.getPacketBuffer();
        int writeIndex = buffer.writerIndex();
        byte[] array = new byte[writeIndex];
        buffer.readBytes(array);
        return array;
    }
}
