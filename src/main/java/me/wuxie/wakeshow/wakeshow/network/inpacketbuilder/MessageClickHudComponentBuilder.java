package me.wuxie.wakeshow.wakeshow.network.inpacketbuilder;

import io.netty.buffer.ByteBuf;
import me.wuxie.wakeshow.wakeshow.network.InPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.client_in.hud.MessageClickHudComponent;

public class MessageClickHudComponentBuilder  implements InPacketBuilder<MessageClickHudComponent> {
    private MessageClickHudComponentBuilder(){}
    public static final MessageClickHudComponentBuilder instance = new MessageClickHudComponentBuilder();
    @Override
    public MessageClickHudComponent builder(ByteBuf buf) {
        return new MessageClickHudComponent(buf);
    }
}
