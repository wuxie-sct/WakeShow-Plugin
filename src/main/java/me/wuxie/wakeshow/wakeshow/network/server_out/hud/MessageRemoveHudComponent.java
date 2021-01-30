package me.wuxie.wakeshow.wakeshow.network.server_out.hud;

import me.wuxie.wakeshow.wakeshow.network.OutPacket;

public class MessageRemoveHudComponent extends OutPacket {
    public MessageRemoveHudComponent(String id) {
        super(8);
        getPacketBuffer().writeString(id);
    }
}
