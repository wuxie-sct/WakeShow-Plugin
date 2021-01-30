package me.wuxie.wakeshow.wakeshow.network.netty;
@Deprecated
public class NettyMessage {

    private Header header;

    private byte[] data;

    public Header getHeader() {
        return header;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "header=" + header +
                ", data=" + data +
                '}';
    }
}