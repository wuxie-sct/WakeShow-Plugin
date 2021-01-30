package me.wuxie.wakeshow.wakeshow.network.server_out;

import me.wuxie.wakeshow.wakeshow.WakeShow;
import me.wuxie.wakeshow.wakeshow.network.OutPacket;

public class MessageDecryptImage extends OutPacket {
    public MessageDecryptImage() {
        super(15);
        getPacketBuffer().writeString(WakeShow.getEncryptKey());
    }
}
