package me.wuxie.wakeshow.wakeshow.network.server_out;

import me.wuxie.wakeshow.wakeshow.WakeShow;
import me.wuxie.wakeshow.wakeshow.network.OutPacket;

public class MessageClientReload extends OutPacket {
    public MessageClientReload() {
        super(16);
        getPacketBuffer().writeString(WakeShow.getEncryptKey());
    }
}
