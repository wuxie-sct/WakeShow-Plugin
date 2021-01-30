package me.wuxie.wakeshow.wakeshow.network.server_out;

import me.wuxie.wakeshow.wakeshow.network.OutPacket;

public class MessageUpdate extends OutPacket {
    public String jsonData;
    public MessageUpdate(String jsonData) {
        super(2);
        this.jsonData = jsonData;
        this.getPacketBuffer().writeString(jsonData);
    }
}
