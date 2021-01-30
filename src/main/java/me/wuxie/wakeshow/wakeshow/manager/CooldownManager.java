package me.wuxie.wakeshow.wakeshow.manager;

import me.wuxie.wakeshow.wakeshow.network.PacketHandler;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageItemCooldown;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
/**
 * 物品冷却管理器
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class CooldownManager {
    private Map<UUID,Map<String,Long>> cooldownMap;

    public CooldownManager(){
        cooldownMap = new HashMap<>();
    }

    /**
     * 设置物品冷却
     * @param player 玩家
     * @param itemStack 物品
     * @param tick 时间
     */
    public void setCooldown(Player player,ItemStack itemStack, int tick){
        if(itemStack!=null&&!itemStack.getType().equals(Material.AIR)){
            ItemStack itemStack1 = itemStack.clone();
            itemStack1.setAmount(1);
            String serialize = itemStack1.toString();
            Map<String,Long> cooldown = cooldownMap.getOrDefault(player.getUniqueId(),new HashMap<>());
            cooldown.put(serialize, (System.currentTimeMillis()+tick*50));
            cooldownMap.put(player.getUniqueId(),cooldown);
            PacketHandler.sendToPlayer(player,new MessageItemCooldown(itemStack,tick));
        }
    }

    /**
     * 判断物品是否冷却完毕
     * @param player 玩家
     * @param itemStack 物品
     * @return 是否冷却完毕
     */
    public boolean isCoolingComplete(Player player, ItemStack itemStack){
        if(itemStack!=null&&!itemStack.getType().equals(Material.AIR)){
            return getCooldown(player,itemStack) <= 0;
        }else{
            return true;
        }
    }

    /**
     * 获得物品的冷却时间
     * @param player 玩家
     * @param itemStack 物品
     * @return 冷却时间 tick
     */
    public int getCooldown(Player player, ItemStack itemStack){
        if(itemStack!=null&&!itemStack.getType().equals(Material.AIR)) {
            Map<String, Long> cooldown = cooldownMap.getOrDefault(player.getUniqueId(), new HashMap<>());
            ItemStack itemStack1 = itemStack.clone();
            itemStack1.setAmount(1);
            String serialize = itemStack1.toString();
            if(cooldown.containsKey(serialize)){
                int has = Math.toIntExact((cooldown.get(serialize) - System.currentTimeMillis()) / 50);
                return Math.max(has, 0);
            }
        }
        return 0;
    }
}
