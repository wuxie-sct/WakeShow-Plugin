package me.wuxie.wakeshow.wakeshow.network.outpacketbuilder;

import me.wuxie.wakeshow.wakeshow.network.OutPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageTick;
@Deprecated
public class MessageTickBuilder implements OutPacketBuilder<MessageTick> {
    public static final MessageTickBuilder instance = new MessageTickBuilder();
    private MessageTickBuilder(){}
    @Override
    public byte[] builder(MessageTick outPacket) {
        PacketBuffer buffer = outPacket.getPacketBuffer();
        int writeIndex = buffer.writerIndex();
        byte[] array = new byte[writeIndex];
        buffer.readBytes(array);
        return array;
    }
}
