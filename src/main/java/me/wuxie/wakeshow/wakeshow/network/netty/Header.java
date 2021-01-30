package me.wuxie.wakeshow.wakeshow.network.netty;

@Deprecated
public class Header {

    /**
     * 4bytes，消息头，用于分割消息。如0xABEF0101
     */
    private int delimiter;

    /**
     * 1byte，类型
     */
    private byte type;

    /**
     * 1byte，保留
     */
    private byte reserved;

    /**
     * 数据长度
     */
    private int length;

    @Override
    public String toString() {
        return "Header{" +
                "delimiter=" + delimiter +
                ", length=" + length +
                ", type=" + type +
                ", reserved=" + reserved +
                '}';
    }
    public byte getReserved() {
        return reserved;
    }

    public void setDelimiter(int delimiter) {
        this.delimiter = delimiter;
    }

    public byte getType() {
        return type;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setReserved(byte reserved) {
        this.reserved = reserved;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getDelimiter() {
        return delimiter;
    }

    public int getLength() {
        return length;
    }
}