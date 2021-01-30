package me.wuxie.wakeshow.wakeshow.network.server_out;

import me.wuxie.wakeshow.wakeshow.network.OutPacket;

@Deprecated
public class MessageTick extends OutPacket {
    public MessageTick(String json) {
        super(4);
    }
}
