package me.wuxie.wakeshow.wakeshow.network.server_out;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.network.OutPacket;

/**
 * 处理特殊组件被人为点击后(会造成持续状态的情况)返回给客户端(是否改变状态)
 */
public class MessageComponentReturnData  extends OutPacket {
    @Getter
    private final String json;
    public MessageComponentReturnData(String type, JsonElement jsonElement) {
        super(3);
        JsonElement je = new JsonObject();
        JsonObject jo = je.getAsJsonObject();
        jo.addProperty("type",type);
        jo.add("data",jsonElement);
        this.json = je.toString();
        this.getPacketBuffer().writeString(json);
    }
}
