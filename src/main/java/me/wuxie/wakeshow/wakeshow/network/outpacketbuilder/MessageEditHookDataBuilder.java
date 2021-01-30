package me.wuxie.wakeshow.wakeshow.network.outpacketbuilder;

import me.wuxie.wakeshow.wakeshow.network.OutPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageEditHookData;

public class MessageEditHookDataBuilder implements OutPacketBuilder<MessageEditHookData> {
    public static final MessageEditHookDataBuilder instance = new MessageEditHookDataBuilder();
    private MessageEditHookDataBuilder(){}
    @Override
    public byte[] builder(MessageEditHookData outPacket) {
        PacketBuffer buffer = outPacket.getPacketBuffer();
        int writeIndex = buffer.writerIndex();
        byte[] array = new byte[writeIndex];
        buffer.readBytes(array);
        return array;
    }
}
