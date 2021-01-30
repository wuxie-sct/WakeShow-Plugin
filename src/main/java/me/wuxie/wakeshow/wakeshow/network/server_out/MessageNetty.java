package me.wuxie.wakeshow.wakeshow.network.server_out;

import me.wuxie.wakeshow.wakeshow.WakeShow;
import me.wuxie.wakeshow.wakeshow.network.OutPacket;
import org.bukkit.configuration.file.FileConfiguration;
@Deprecated
public class MessageNetty extends OutPacket {
    public MessageNetty() {
        super(17);
        FileConfiguration c = WakeShow.getPlugin().getConfig();
        getPacketBuffer().writeString(c.getString("nettyServer.ip"));
        getPacketBuffer().writeInt(c.getInt("nettyServer.port"));
    }
}
