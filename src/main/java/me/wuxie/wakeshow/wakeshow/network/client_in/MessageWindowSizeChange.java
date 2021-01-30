package me.wuxie.wakeshow.wakeshow.network.client_in;

import io.netty.buffer.ByteBuf;
import me.wuxie.wakeshow.wakeshow.api.event.PlayerWindowChangeEvent;
import me.wuxie.wakeshow.wakeshow.api.event.WClientProofreadingEvent;
import me.wuxie.wakeshow.wakeshow.network.InPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageWindowSizeChange extends InPacket {

    public MessageWindowSizeChange(ByteBuf buf) {
        super(4, buf);
    }

    @Override
    public void handlePacket(Player player) {
        int width = packetBuffer.readInt();
        int height = packetBuffer.readInt();
        PlayerWindowChangeEvent event = new PlayerWindowChangeEvent(player,width,height);
        Bukkit.getPluginManager().callEvent(event);
        boolean hookReturn = packetBuffer.readBoolean();
        /* hook 消息发送的窗口改变事件，触发客户端校对事件 */
        if(hookReturn){
            WClientProofreadingEvent proofreadingEvent = new WClientProofreadingEvent(player);
            Bukkit.getPluginManager().callEvent(proofreadingEvent);
        }
    }
}
