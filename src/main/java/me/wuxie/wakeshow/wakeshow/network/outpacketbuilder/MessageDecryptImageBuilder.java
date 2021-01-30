package me.wuxie.wakeshow.wakeshow.network.outpacketbuilder;

import me.wuxie.wakeshow.wakeshow.network.OutPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageDecryptImage;

public class MessageDecryptImageBuilder implements OutPacketBuilder<MessageDecryptImage> {
    public static final MessageDecryptImageBuilder instance = new MessageDecryptImageBuilder();
    private MessageDecryptImageBuilder(){}
    @Override
    public byte[] builder(MessageDecryptImage outPacket) {
        PacketBuffer buffer = outPacket.getPacketBuffer();
        int writeIndex = buffer.writerIndex();
        byte[] array = new byte[writeIndex];
        buffer.readBytes(array);
        return array;
    }
}
