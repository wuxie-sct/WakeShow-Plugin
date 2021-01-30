package me.wuxie.wakeshow.wakeshow.network.client_in;

import io.netty.buffer.ByteBuf;
import me.wuxie.wakeshow.wakeshow.api.WuxieAPI;
import me.wuxie.wakeshow.wakeshow.network.InPacket;
import me.wuxie.wakeshow.wakeshow.ui.OpenedGui;
import org.bukkit.entity.Player;

public class MessageCloseGui extends InPacket {
    public MessageCloseGui(ByteBuf buf) {
        super(1,buf);
    }

    @Override
    public void handlePacket(Player player) {
        String screenId = getPacketBuffer().readString();
        OpenedGui openedGui = WuxieAPI.getOpenedGui(player);
        if(openedGui==null)return;
        if(openedGui.getScreen().getId().equals(screenId)){
            openedGui.close_Client();
        }
    }
}
