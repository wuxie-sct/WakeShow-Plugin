package me.wuxie.wakeshow.wakeshow.network.outpacketbuilder;

import me.wuxie.wakeshow.wakeshow.network.OutPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageComponentReturnData;

public class MessageComponentReturnDataBuilder implements OutPacketBuilder<MessageComponentReturnData> {
    public static final MessageComponentReturnDataBuilder instance = new MessageComponentReturnDataBuilder();
    private MessageComponentReturnDataBuilder(){}
    @Override
    public byte[] builder(MessageComponentReturnData outPacket) {
        PacketBuffer buffer = outPacket.getPacketBuffer();
        int writeIndex = buffer.writerIndex();
        byte[] array = new byte[writeIndex];
        buffer.readBytes(array);
        return array;
    }
}
