package me.wuxie.wakeshow.wakeshow.network.outpacketbuilder;

import me.wuxie.wakeshow.wakeshow.network.OutPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageCloseGui;

public class MessageCloseGuiBuilder implements OutPacketBuilder<MessageCloseGui> {
    public static final MessageCloseGuiBuilder instance = new MessageCloseGuiBuilder();
    private MessageCloseGuiBuilder(){}
    @Override
    public byte[] builder(MessageCloseGui outPacket) {
        PacketBuffer buffer = outPacket.getPacketBuffer();
        int writeIndex = buffer.writerIndex();
        byte[] array = new byte[writeIndex];
        buffer.readBytes(array);
        return array;
    }
}
