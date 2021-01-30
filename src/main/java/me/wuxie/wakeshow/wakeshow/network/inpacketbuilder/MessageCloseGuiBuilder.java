package me.wuxie.wakeshow.wakeshow.network.inpacketbuilder;

import io.netty.buffer.ByteBuf;
import me.wuxie.wakeshow.wakeshow.network.client_in.MessageCloseGui;
import me.wuxie.wakeshow.wakeshow.network.InPacketBuilder;

public class MessageCloseGuiBuilder implements InPacketBuilder<MessageCloseGui> {
    public static final MessageCloseGuiBuilder instance = new MessageCloseGuiBuilder();
    private MessageCloseGuiBuilder(){

    }

    @Override
    public MessageCloseGui builder(ByteBuf buf) {
        return new MessageCloseGui(buf);
    }
}
