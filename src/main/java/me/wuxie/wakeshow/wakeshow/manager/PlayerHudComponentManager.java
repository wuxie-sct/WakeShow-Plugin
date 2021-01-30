package me.wuxie.wakeshow.wakeshow.manager;

import me.wuxie.wakeshow.wakeshow.network.PacketHandler;
import me.wuxie.wakeshow.wakeshow.network.server_out.hud.MessageAddHudComponent;
import me.wuxie.wakeshow.wakeshow.network.server_out.hud.MessageRemoveHudComponent;
import me.wuxie.wakeshow.wakeshow.ui.hudcomponent.HudComponent;
import me.wuxie.wakeshow.wakeshow.util.JsonUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 玩家HUD管理器
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class PlayerHudComponentManager {
    private Map<UUID,Map<String, HudComponent>> hudComponentMap;
    public PlayerHudComponentManager(){
        hudComponentMap = new HashMap<>();
    }

    public void addHudComponent(Player player,HudComponent component){
        if(component!=null&&player!=null&&player.isOnline()) {
            getPlayerData(player).put(component.getId(), component);
            // 发包
            PacketHandler.sendToPlayer(player, new MessageAddHudComponent(JsonUtil.hudComponentToJson(component)));
        }
    }

    public void removeHudComponent(Player player,String id){
        if(id!=null&&player!=null&&player.isOnline()) {
            if(getPlayerData(player).containsKey(id)) {
                getPlayerData(player).remove(id);
                // 发包
                PacketHandler.sendToPlayer(player, new MessageRemoveHudComponent(id));
            }
        }
    }
    public HudComponent getPlayerHudComponent(Player player,String id){
        if(id!=null&&player!=null&&player.isOnline()) {
            return getPlayerData(player).get(id);
        }
        return null;
    }


    /**
     * 只有在玩家退出时执行
     * @param player 玩家
     */
    public void removePlayerData(Player player){
        hudComponentMap.remove(player.getUniqueId());
    }

    public Map<String, HudComponent> getPlayerData(Player player){
        if(hudComponentMap.containsKey(player.getUniqueId())){
            return hudComponentMap.get(player.getUniqueId());
        }else {
            Map<String, HudComponent> map = new HashMap<>();
            hudComponentMap.put(player.getUniqueId(),map);
            return map;
        }
    }

}
