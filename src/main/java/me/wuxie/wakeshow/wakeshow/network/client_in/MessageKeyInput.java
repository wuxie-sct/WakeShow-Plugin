package me.wuxie.wakeshow.wakeshow.network.client_in;

import io.netty.buffer.ByteBuf;
import me.wuxie.wakeshow.wakeshow.api.event.WKeyInputEvent;
import me.wuxie.wakeshow.wakeshow.network.InPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageKeyInput extends InPacket {
    private int typeId;
    private int keyId;
    public MessageKeyInput(ByteBuf buf) {
        super(7, buf);
        typeId = buf.readByte();
        keyId = buf.readInt();
    }
    @Override
    public void handlePacket(Player player) {
        WKeyInputEvent keyInputEvent = new WKeyInputEvent(player,typeId==0? WKeyInputEvent.KeyType.PRESS: WKeyInputEvent.KeyType.RELEASE,keyId);
        Bukkit.getPluginManager().callEvent(keyInputEvent);
    }
}
