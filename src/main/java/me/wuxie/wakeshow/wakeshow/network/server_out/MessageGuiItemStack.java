package me.wuxie.wakeshow.wakeshow.network.server_out;

import me.wuxie.wakeshow.wakeshow.network.OutPacket;
import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class MessageGuiItemStack extends OutPacket {
    /**
     * 用来分段发送gui的物品数据
     * @param slotPath 路径
     * @param slotSize gui的总槽位数
     * @param itemStack 物品
     */
    public MessageGuiItemStack(String slotPath, int slotSize, ItemStack itemStack) {
        super(12);
        PacketBuffer buffer = getPacketBuffer();
        buffer.writeInt(slotSize);
        buffer.writeString(slotPath);
        buffer.writeItemStack(itemStack);
    }
}
