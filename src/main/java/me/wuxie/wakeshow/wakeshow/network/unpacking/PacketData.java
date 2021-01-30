package me.wuxie.wakeshow.wakeshow.network.unpacking;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import java.util.List;

import static me.wuxie.wakeshow.wakeshow.network.unpacking.PacketHead.DELIMITER;

public class PacketData {
    public PacketData(byte[] data){
        this.data = data;
    }
    PacketHead packetHead;
    @Getter
    @Setter
    private byte[] data;

    /**
     * @return 将字节数组分段处理然后返回
     */
    public List<byte[]> getByteList(){
        int maxLength = 32700;
        int node =  data.length/maxLength;
        if(node * maxLength < data.length){
            node+=1;
        }
        ByteBuf byteBuf = Unpooled.wrappedBuffer(data);
        List<byte[]> byteList = new ArrayList<>();
        PacketHead head = new PacketHead();
        //head.sign = (char)('\u0000' + Math.random() * ('\uFFFF' + 1));
        head.dataNode = node;
        head.dataLength = data.length;

        for (int a = 0;a<node;){
            int byteIndex = maxLength;
            if(byteBuf.readableBytes()<byteIndex){
                byteIndex = byteBuf.readableBytes();
            }
            byte[] bytes = new byte[byteIndex];
            byteBuf.readBytes(bytes);
            // 索引到已读的位置
            byteBuf.readerIndex(byteIndex);
            // 回收已读
            byteBuf.discardReadBytes();

            head.currentNode = a+1;

            ByteBuf buf = Unpooled.buffer();// 20 = 头长
            buf.writeInt(DELIMITER);
            buf.writeInt(head.dataLength);
            buf.writeInt(head.dataNode);
            buf.writeInt(head.currentNode);
            buf.writeInt(bytes.length);
            buf.writeBytes(bytes);
            int writeIndex = buf.writerIndex();
            byte[] array = new byte[writeIndex];
            buf.readBytes(array);
            //System.out.println("A "+array.length);
            //System.out.println("A "+bytes1.length);
            //buf.readerIndex(20);
            //buf.discardReadBytes();
            //System.out.println(Arrays.toString(buf.array()));
            /*System.out.println(     "字节包段: ["+
                                    a+"/"+node+
                                    "], 包头长: ["+
                                        headByte.length +
                                    "], 包身长: ["+
                                        len+
                                    "], 头身共长: ["+
                                        bytes.length+
                                    "], 总长: ["+data.length+"]");*/
            byteList.add(array);
            a+=1;
        }
        return byteList;
    }


    public static byte[] joinByteArray(byte[] byte1, byte[] byte2) {
        return ByteBuffer
                .allocate(byte1.length + byte2.length)
                .put(byte1)
                .put(byte2)
                .array();
    }

    public void joinByteArray(byte[] bytes){
        data = joinByteArray(data,bytes);
    }

    public static void main(String[] args) {
        //new PacketData(new byte[]{1,2,3,4,5,6,7,8,9,10}).getByteList();
        new PacketData(new byte[100000]).getByteList();
    }
}
