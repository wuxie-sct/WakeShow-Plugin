package me.wuxie.wakeshow.wakeshow.network.server_out.hud;

import com.google.gson.JsonElement;
import me.wuxie.wakeshow.wakeshow.network.OutPacket;

public class MessageAddHudComponent extends OutPacket {
    public MessageAddHudComponent(JsonElement je) {
        super(7);
        getPacketBuffer().writeString(je.toString());
    }
}
