package me.wuxie.wakeshow.wakeshow.network.outpacketbuilder;

import me.wuxie.wakeshow.wakeshow.network.OutPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import me.wuxie.wakeshow.wakeshow.network.server_out.hud.MessageAddHudComponent;

public class MessageAddHudComponentBuilder implements OutPacketBuilder<MessageAddHudComponent> {
    public static final MessageAddHudComponentBuilder instance = new MessageAddHudComponentBuilder();
    private MessageAddHudComponentBuilder(){}
    @Override
    public byte[] builder(MessageAddHudComponent outPacket) {
        PacketBuffer buffer = outPacket.getPacketBuffer();
        int writeIndex = buffer.writerIndex();
        byte[] array = new byte[writeIndex];
        buffer.readBytes(array);
        return array;
    }
}
