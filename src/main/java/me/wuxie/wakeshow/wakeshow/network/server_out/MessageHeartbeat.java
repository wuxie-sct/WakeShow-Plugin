package me.wuxie.wakeshow.wakeshow.network.server_out;

import me.wuxie.wakeshow.wakeshow.WakeShow;
import me.wuxie.wakeshow.wakeshow.network.OutPacket;

@Deprecated
public class MessageHeartbeat  extends OutPacket {
    public MessageHeartbeat() {
        super(11);
        //getPacketBuffer().writeInt(WakeShow.getHeartbeatKey());
    }
}
