package me.wuxie.wakeshow.wakeshow.network.client_in.hud;

import io.netty.buffer.ByteBuf;
import me.wuxie.wakeshow.wakeshow.api.WuxieAPI;
import me.wuxie.wakeshow.wakeshow.network.InPacket;
import me.wuxie.wakeshow.wakeshow.ui.hudcomponent.HudComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MessageClickHudComponent extends InPacket {
    public MessageClickHudComponent(ByteBuf buf) {
        super(5,buf);
    }

    @Override
    public void handlePacket(Player player) {
        int mouseButtonId = getPacketBuffer().readInt();
        HudComponent hudComponent = WuxieAPI.getPlayerHudComponent(player,getPacketBuffer().readString());
        hudComponent.onClick(mouseButtonId,player);
    }
}
