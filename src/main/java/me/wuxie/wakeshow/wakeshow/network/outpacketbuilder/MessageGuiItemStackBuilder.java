package me.wuxie.wakeshow.wakeshow.network.outpacketbuilder;

import me.wuxie.wakeshow.wakeshow.network.OutPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageGuiItemStack;

public class MessageGuiItemStackBuilder implements OutPacketBuilder<MessageGuiItemStack> {
    public static final MessageGuiItemStackBuilder instance = new MessageGuiItemStackBuilder();
    private MessageGuiItemStackBuilder(){}
    @Override
    public byte[] builder(MessageGuiItemStack outPacket) {
        PacketBuffer buffer = outPacket.getPacketBuffer();
        int writeIndex = buffer.writerIndex();
        byte[] array = new byte[writeIndex];
        buffer.readBytes(array);
        return array;
    }
}
