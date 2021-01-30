package me.wuxie.wakeshow.wakeshow.util;

import net.minecraft.server.v1_12_R1.EntityItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftItem;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerUtil {
    public static PlayerDropItemEvent dropItem(Player player, ItemStack itemStack){
        Location location = player.getEyeLocation();
        EntityItem entity = new EntityItem(((CraftWorld) player.getWorld()).getHandle(),
                location.getX(), location.getY(),
                location.getZ(), CraftItemStack.asNMSCopy(itemStack));
        entity.pickupDelay = 10;
        CraftItem craftItem = new CraftItem((CraftServer) Bukkit.getServer(), entity);
        PlayerDropItemEvent playerDropItemEvent = new PlayerDropItemEvent(player,craftItem);
        Bukkit.getPluginManager().callEvent(playerDropItemEvent);
        if(!playerDropItemEvent.isCancelled()){
            ((CraftWorld) player.getWorld()).addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
            craftItem.setPickupDelay(35);
            craftItem.setVelocity(player.getLocation().clone().getDirection().setY(0.1).multiply(0.25));
        }
        return playerDropItemEvent;
    }
}
