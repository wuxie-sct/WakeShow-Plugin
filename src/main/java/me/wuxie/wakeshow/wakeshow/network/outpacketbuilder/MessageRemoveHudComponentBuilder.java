package me.wuxie.wakeshow.wakeshow.network.outpacketbuilder;

import me.wuxie.wakeshow.wakeshow.network.OutPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import me.wuxie.wakeshow.wakeshow.network.server_out.hud.MessageRemoveHudComponent;

public class MessageRemoveHudComponentBuilder implements OutPacketBuilder<MessageRemoveHudComponent> {
    public static final MessageRemoveHudComponentBuilder instance = new MessageRemoveHudComponentBuilder();

    private MessageRemoveHudComponentBuilder() {
    }

    @Override
    public byte[] builder(MessageRemoveHudComponent outPacket) {
        PacketBuffer buffer = outPacket.getPacketBuffer();
        int writeIndex = buffer.writerIndex();
        byte[] array = new byte[writeIndex];
        buffer.readBytes(array);
        return array;
    }
}
