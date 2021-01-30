package me.wuxie.wakeshow.wakeshow.network.inpacketbuilder;

import io.netty.buffer.ByteBuf;
import me.wuxie.wakeshow.wakeshow.network.InPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.client_in.MessageKeyInput;

public class MessageKeyInputBuilder implements InPacketBuilder<MessageKeyInput> {
    public static final MessageKeyInputBuilder instance = new MessageKeyInputBuilder();
    private MessageKeyInputBuilder(){}
    @Override
    public MessageKeyInput builder(ByteBuf buf) {
        return new MessageKeyInput(buf);
    }
}
