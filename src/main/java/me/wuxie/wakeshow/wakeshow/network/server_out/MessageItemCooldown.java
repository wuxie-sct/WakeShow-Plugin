package me.wuxie.wakeshow.wakeshow.network.server_out;

import me.wuxie.wakeshow.wakeshow.network.OutPacket;
import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class MessageItemCooldown extends OutPacket {
    public MessageItemCooldown(ItemStack itemStack, int tick) {
        super(9);
        PacketBuffer buffer = getPacketBuffer();
        buffer.writeInt(tick);
        buffer.writeItemStack(itemStack);
    }
}
