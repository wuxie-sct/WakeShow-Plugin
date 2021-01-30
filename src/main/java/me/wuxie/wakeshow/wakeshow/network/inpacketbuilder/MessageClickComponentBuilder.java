package me.wuxie.wakeshow.wakeshow.network.inpacketbuilder;

import io.netty.buffer.ByteBuf;
import me.wuxie.wakeshow.wakeshow.network.InPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.client_in.MessageClickComponent;

public class MessageClickComponentBuilder implements InPacketBuilder<MessageClickComponent> {
    private MessageClickComponentBuilder(){}
    public static final MessageClickComponentBuilder instance = new MessageClickComponentBuilder();
    @Override
    public MessageClickComponent builder(ByteBuf buf) {
        return new MessageClickComponent(buf);
    }
}
