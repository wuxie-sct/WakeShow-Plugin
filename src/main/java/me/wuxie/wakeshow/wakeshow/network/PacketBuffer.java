package me.wuxie.wakeshow.wakeshow.network;

import io.netty.buffer.ByteBuf;

import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.nio.charset.StandardCharsets;

public class PacketBuffer extends PacketDataSerializer {
    public void writeItemStack(ItemStack itemStack) {
        this.a(CraftItemStack.asNMSCopy(itemStack));
    }

    public PacketBuffer writeString(String string) {
        byte[] abyte = string.getBytes(StandardCharsets.UTF_8);
        this.d(abyte.length);
        this.writeBytes(abyte);
        return this;
    }

    public PacketBuffer(ByteBuf byteBuf) {
        super(byteBuf);
    }

    public String readString(int length) {
        return this.e(length);
    }

    public String readString() {
        int j = this.g();
        String s = this.toString(this.readerIndex(), j, StandardCharsets.UTF_8);
        this.readerIndex(this.readerIndex() + j);
        return s;
    }

    public ItemStack readItemStack() {
        return CraftItemStack.asBukkitCopy(this.k());
    }
}