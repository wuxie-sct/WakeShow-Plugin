package me.wuxie.wakeshow.wakeshow.network.outpacketbuilder;

import me.wuxie.wakeshow.wakeshow.network.OutPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageDownloadImage;

public class MessageDownloadImageBuilder implements OutPacketBuilder<MessageDownloadImage> {
    public static final MessageDownloadImageBuilder instance = new MessageDownloadImageBuilder();
    private MessageDownloadImageBuilder(){}
    @Override
    public byte[] builder(MessageDownloadImage outPacket) {
        PacketBuffer buffer = outPacket.getPacketBuffer();
        int writeIndex = buffer.writerIndex();
        byte[] array = new byte[writeIndex];
        buffer.readBytes(array);
        return array;
    }
}
