package me.wuxie.wakeshow.wakeshow.network.netty;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
@Deprecated
public class NettyMessageDecoder extends ByteToMessageDecoder {

    /**
     * 消息体字节大小：分割符字段4字节+长度字段4字节+请求类型字典1字节+预留字段1字节=10字节
     */
    private static final int HEAD_LENGTH = 10;
    public static final int DELIMITER = 0xABEF0101;
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        while (true) {
            // 标记字节流开始位置
            byteBuf.markReaderIndex();

            // 若读取到分割标识，说明读取当前字节流开始位置了
            if (byteBuf.readInt() == DELIMITER) {
                break;
            }

            // 重置读索引为0
            byteBuf.resetReaderIndex();

            // 长度校验，字节流长度至少10字节，小于10字节则等待下一次字节流过来
            if (byteBuf.readableBytes() < HEAD_LENGTH) {
                byteBuf.resetReaderIndex();
                return;
            }
        }

        // 2、获取data的字节流长度
        int dataLength = byteBuf.readInt();

        // 校验数据包是否全部发送过来，总字节流长度（此处读取的是除去delimiter和length之后的总长度）-
        // type和reserved两个字节=data的字节流长度
        int totalLength = byteBuf.readableBytes();
        if ((totalLength - 2) < dataLength) {

            // 长度校验，字节流长度少于数据包长度，说明数据包拆包了，等待下一次字节流过来
            byteBuf.resetReaderIndex();
            return;
        }

        // 3、请求类型
        byte type = byteBuf.readByte();

        // 4、预留字段
        byte reserved = byteBuf.readByte();


        // 5、数据包内容
        byte[] data = null;
        if (dataLength > 0) {
            data = new byte[dataLength];
            byteBuf.readBytes(data);
        }

        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setDelimiter(DELIMITER);
        header.setLength(dataLength);
        header.setType(type);
        header.setReserved(reserved);
        nettyMessage.setHeader(header);
        nettyMessage.setData(data);

        list.add(nettyMessage);

        // 回收已读字节
        byteBuf.discardReadBytes();
    }
}