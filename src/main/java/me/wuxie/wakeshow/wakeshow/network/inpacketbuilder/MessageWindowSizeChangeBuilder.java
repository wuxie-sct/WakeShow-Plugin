package me.wuxie.wakeshow.wakeshow.network.inpacketbuilder;

import io.netty.buffer.ByteBuf;
import me.wuxie.wakeshow.wakeshow.network.InPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.client_in.MessageWindowSizeChange;

public class MessageWindowSizeChangeBuilder implements InPacketBuilder<MessageWindowSizeChange> {
    public static final MessageWindowSizeChangeBuilder instance = new MessageWindowSizeChangeBuilder();
    private MessageWindowSizeChangeBuilder(){}
    @Override
    public MessageWindowSizeChange builder(ByteBuf buf) {
        return new MessageWindowSizeChange(buf);
    }
}
