package me.wuxie.wakeshow.wakeshow.network;

public interface OutPacketBuilder<I extends OutPacket> {
    // 将数据包对象处理成字节数组
    byte[] builder(I outPacket);
}
