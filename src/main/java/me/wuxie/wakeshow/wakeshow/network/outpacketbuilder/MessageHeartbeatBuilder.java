package me.wuxie.wakeshow.wakeshow.network.outpacketbuilder;

import me.wuxie.wakeshow.wakeshow.network.OutPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageHeartbeat;

@Deprecated
public class MessageHeartbeatBuilder implements OutPacketBuilder<MessageHeartbeat> {
    public static final MessageHeartbeatBuilder instance = new MessageHeartbeatBuilder();
    private MessageHeartbeatBuilder(){}
    @Override
    public byte[] builder(MessageHeartbeat outPacket) {
        PacketBuffer buffer = outPacket.getPacketBuffer();
        int writeIndex = buffer.writerIndex();
        byte[] array = new byte[writeIndex];
        buffer.readBytes(array);
        return array;
    }
}