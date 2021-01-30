package me.wuxie.wakeshow.wakeshow.network.inpacketbuilder;

import io.netty.buffer.ByteBuf;
import me.wuxie.wakeshow.wakeshow.network.InPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.client_in.MessageNettyLogin;
@Deprecated
public class MessageNettyLoginBuilder implements InPacketBuilder<MessageNettyLogin> {
    public static final MessageNettyLoginBuilder instance = new MessageNettyLoginBuilder();
    private MessageNettyLoginBuilder(){}
    @Override
    public MessageNettyLogin builder(ByteBuf buf) {
        return new MessageNettyLogin(buf);
    }
}
