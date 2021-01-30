package me.wuxie.wakeshow.wakeshow.network.server_out;

import me.wuxie.wakeshow.wakeshow.network.OutPacket;

public class MessageEditHookData extends OutPacket {
    public MessageEditHookData(boolean addOrRemove,String data) {
        super(10);
        getPacketBuffer().writeBoolean(addOrRemove);
        getPacketBuffer().writeString(data);
    }
}
