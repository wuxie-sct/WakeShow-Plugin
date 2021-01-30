package me.wuxie.wakeshow.wakeshow.network.inpacketbuilder;

import io.netty.buffer.ByteBuf;
import me.wuxie.wakeshow.wakeshow.network.InPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.client_in.MessageClickInventorySlot;

public class MessageClickInventorySlotBuilder implements InPacketBuilder<MessageClickInventorySlot> {
    private MessageClickInventorySlotBuilder(){}
    public static final MessageClickInventorySlotBuilder instance = new MessageClickInventorySlotBuilder();
    @Override
    public MessageClickInventorySlot builder(ByteBuf buf) {
        return new MessageClickInventorySlot(buf);
    }
}
