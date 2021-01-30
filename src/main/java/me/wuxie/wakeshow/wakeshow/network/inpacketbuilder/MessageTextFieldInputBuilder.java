package me.wuxie.wakeshow.wakeshow.network.inpacketbuilder;

import io.netty.buffer.ByteBuf;
import me.wuxie.wakeshow.wakeshow.network.InPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.client_in.MessageTextFieldInput;

public class MessageTextFieldInputBuilder implements InPacketBuilder<MessageTextFieldInput> {
    public static final MessageTextFieldInputBuilder instance = new MessageTextFieldInputBuilder();
    private MessageTextFieldInputBuilder(){}
    @Override
    public MessageTextFieldInput builder(ByteBuf buf) {
        return new MessageTextFieldInput(buf);
    }
}
