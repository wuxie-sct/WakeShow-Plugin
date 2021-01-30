package me.wuxie.wakeshow.wakeshow.network.inpacketbuilder;

import io.netty.buffer.ByteBuf;
import me.wuxie.wakeshow.wakeshow.network.InPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.client_in.MessageHeartbeat;

@Deprecated
public class MessageHeartbeatBuilder implements InPacketBuilder<MessageHeartbeat> {
    private MessageHeartbeatBuilder(){}
    public static final MessageHeartbeatBuilder instance = new MessageHeartbeatBuilder();
    @Override
    public MessageHeartbeat builder(ByteBuf buf) {
        return new MessageHeartbeat(buf);
    }
}
