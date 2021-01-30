package me.wuxie.wakeshow.wakeshow.network.unpacking;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

// 包头标识
public class PacketHead {
    public static final int DELIMITER = 0xABEF0101;
    // 2 字节总长
    int dataLength;
    // 2 被拆分了多少节点
    int dataNode;
    // 2 当前是第几节点
    int currentNode;
    // 2 组合标识符
    //char sign;

    /*public byte[] toBytes(){
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeInt(DELIMITER);
        byteBuf.writeInt(dataLength);
        byteBuf.writeInt(dataNode);
        byteBuf.writeInt(currentNode);
        return byteBuf.array();
    }*/
}
