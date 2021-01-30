package me.wuxie.wakeshow.wakeshow.network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
@Deprecated
public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NettyMessage nettyMessage, ByteBuf byteBuf) throws Exception {

        if (nettyMessage == null || nettyMessage.getHeader() == null) {
            throw new Exception("The nettyMessage is null.");
        }

        // 1、写入分割标志
        byteBuf.writeInt(nettyMessage.getHeader().getDelimiter());

        // 2、写入数据包长度
        byteBuf.writeInt(nettyMessage.getData() != null ? nettyMessage.getData().length : 0);

        // 3、写入请求类型
        byteBuf.writeByte(nettyMessage.getHeader().getType());

        // 4、写入预留字段
        byteBuf.writeByte(nettyMessage.getHeader().getReserved());

        // 5、写入数据
        byteBuf.writeBytes(nettyMessage.getData() != null ? nettyMessage.getData() : null);

    }
}