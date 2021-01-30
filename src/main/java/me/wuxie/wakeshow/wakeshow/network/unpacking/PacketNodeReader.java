package me.wuxie.wakeshow.wakeshow.network.unpacking;

import io.netty.buffer.ByteBuf;

// 包节点读取方法
public class PacketNodeReader {

    public static PacketData packetData;

    public void read(ByteBuf byteBuf){
        // 确定分割标志
        if(byteBuf.readInt()==0xABEF0101){
            // 包总长
            int dataLength = byteBuf.readInt();
            // 包总节点
            int dataNode = byteBuf.readInt();
            // 当前分包节点
            int currentNode = byteBuf.readInt();
            // 当前分包长度
            int currentLength = byteBuf.readInt();
            // 回收已读
            byteBuf.discardReadBytes();
            byte[] bytes = new byte[currentLength];
            byteBuf.readBytes(bytes);
            PacketHead packetHead = packetData!=null?packetData.packetHead:null;
            if(currentNode == 1) {
                packetData = new PacketData(bytes);
                PacketHead head = new PacketHead();
                head.currentNode = 1;
                head.dataNode = dataNode;
                head.dataLength = dataLength;
            } else if(packetHead != null && packetHead.currentNode+1 == currentNode){
                packetHead.currentNode+=1;
                packetData.joinByteArray(bytes);
                packetData.packetHead.dataLength+=dataLength;
                if(currentNode == dataNode){
                    if(packetData.getData().length == packetHead.dataLength){
                        handler(packetData.getData());
                    } else {
                        System.out.println("产生了错误的分包! 包长度不一!");
                    }
                }
            } else {
                System.out.println("产生了错误的分包! 缺失包/顺序错乱!");
            }
        }
    }

    public void handler(byte[] bytes){

    }
}
