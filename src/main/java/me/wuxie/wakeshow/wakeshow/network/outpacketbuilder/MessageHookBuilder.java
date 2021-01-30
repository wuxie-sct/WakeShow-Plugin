package me.wuxie.wakeshow.wakeshow.network.outpacketbuilder;

import me.wuxie.wakeshow.wakeshow.network.OutPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageHook;

public class MessageHookBuilder implements OutPacketBuilder<MessageHook> {
    public static final MessageHookBuilder instance = new MessageHookBuilder();
    private MessageHookBuilder(){}
    @Override
    public byte[] builder(MessageHook outPacket) {
        PacketBuffer buffer = outPacket.getPacketBuffer();
        int writeIndex = buffer.writerIndex();
        byte[] array = new byte[writeIndex];
        buffer.readBytes(array);
        return array;
    }
}
