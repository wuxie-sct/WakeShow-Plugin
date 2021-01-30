package me.wuxie.wakeshow.wakeshow.network;

import io.netty.buffer.ByteBuf;

public interface InPacketBuilder<I extends InPacket> {
    // 将ByteBuf对象处理成数据包对象
    I builder(ByteBuf buf);
}
