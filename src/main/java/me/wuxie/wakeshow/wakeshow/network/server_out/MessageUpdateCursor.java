package me.wuxie.wakeshow.wakeshow.network.server_out;

import me.wuxie.wakeshow.wakeshow.network.OutPacket;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class MessageUpdateCursor extends OutPacket {
    public MessageUpdateCursor(String gui, ItemStack itemStack) {
        super(18);
        getPacketBuffer().writeString(gui);
        getPacketBuffer().writeItemStack(itemStack);
    }
}
