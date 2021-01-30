package me.wuxie.wakeshow.wakeshow.network.server_out;

import me.wuxie.wakeshow.wakeshow.network.OutPacket;

/**
 * 分包发送的数据包
 */
public class MessageUnpacking extends OutPacket {
    public MessageUnpacking(byte[] bytes) {
        super(19);
        getPacketBuffer().writeBytes(bytes);
    }
}
