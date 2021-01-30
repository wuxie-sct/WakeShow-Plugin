package me.wuxie.wakeshow.wakeshow.network.server_out;

import me.wuxie.wakeshow.wakeshow.network.OutPacket;

import me.wuxie.wakeshow.wakeshow.ui.component.WSlot;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class MessageUpdateSlot extends OutPacket {
    public MessageUpdateSlot(WSlot slot) {
        super(4);
        ItemStack itemStack = slot.getItemStack();
        if(itemStack==null){
            itemStack = new ItemStack(Material.AIR);
        }
        this.getPacketBuffer().writeString(slot.getPath());
        this.getPacketBuffer().writeBoolean(slot.isCanDrag());
        this.getPacketBuffer().writeBoolean(slot.isFollowOffset());
        this.getPacketBuffer().writeInt(slot.getX());
        this.getPacketBuffer().writeInt(slot.getY());
        this.getPacketBuffer().writeInt(slot.getZ()==0?100:slot.getZ());
        this.getPacketBuffer().writeFloat(slot.getScale());
        this.getPacketBuffer().writeItemStack(itemStack);
    }
}
