package me.wuxie.wakeshow.wakeshow.network.outpacketbuilder;

import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageUpdateSlot;
import me.wuxie.wakeshow.wakeshow.network.OutPacketBuilder;

public class MessageUpdateSlotBuilder implements OutPacketBuilder<MessageUpdateSlot> {
    public static final MessageUpdateSlotBuilder instance = new MessageUpdateSlotBuilder();
    private MessageUpdateSlotBuilder(){}
    @Override
    public byte[] builder(MessageUpdateSlot outPacket) {
        PacketBuffer buffer = outPacket.getPacketBuffer();
        int writeIndex = buffer.writerIndex();
        byte[] array = new byte[writeIndex];
        buffer.readBytes(array);
        return array;
    }
}
