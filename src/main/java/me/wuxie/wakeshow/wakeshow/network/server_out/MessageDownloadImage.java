package me.wuxie.wakeshow.wakeshow.network.server_out;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import me.wuxie.wakeshow.wakeshow.network.OutPacket;

import java.util.List;

public class MessageDownloadImage  extends OutPacket {
    public MessageDownloadImage(List<String> urls) {
        super(5);
        JsonElement je = new Gson().toJsonTree(urls);
        getPacketBuffer().writeString(je.toString());
    }
}
