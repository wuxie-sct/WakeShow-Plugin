package me.wuxie.wakeshow.wakeshow.network.inpacketbuilder;

import io.netty.buffer.ByteBuf;
import me.wuxie.wakeshow.wakeshow.network.InPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.client_in.MessageRequestHook;

public class MessageRequestHookBuilder implements InPacketBuilder<MessageRequestHook> {
    private MessageRequestHookBuilder(){}
    public static final MessageRequestHookBuilder instance = new MessageRequestHookBuilder();
    @Override
    public MessageRequestHook builder(ByteBuf buf) {
        return new MessageRequestHook(buf);
    }
}
