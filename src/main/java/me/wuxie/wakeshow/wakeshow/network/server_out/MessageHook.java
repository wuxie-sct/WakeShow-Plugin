package me.wuxie.wakeshow.wakeshow.network.server_out;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import me.wuxie.wakeshow.wakeshow.network.OutPacket;

import java.util.List;
import java.util.Set;

/**
 * 1s 内连续的Hook消息发送将会被客户端阻断第一个之后的Hook消息
 */
public class MessageHook extends OutPacket {
    public MessageHook(Set<String> hookData) {
        super(6);
        JsonElement je = new Gson().toJsonTree(hookData);
        getPacketBuffer().writeString(je.toString());
    }
}
