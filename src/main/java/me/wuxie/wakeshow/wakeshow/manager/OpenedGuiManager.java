package me.wuxie.wakeshow.wakeshow.manager;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.WakeShow;
import me.wuxie.wakeshow.wakeshow.ui.OpenedGui;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
/**
 * 玩家打开的GUI管理器
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class OpenedGuiManager {
    @Getter
    private Map<UUID, OpenedGui> openedGuiMap;
    private BukkitTask task = null;
    public OpenedGuiManager(){
        reload();
    }
    public void reload(){
        openedGuiMap = new HashMap<>();
        if(task!=null&&!task.isCancelled()){
            task.cancel();
        }
        task = new BukkitRunnable() {
            @Override
            public void run() {
                for (OpenedGui openedGui:openedGuiMap.values()){
                    openedGui.tick();
                }
            }
        }.runTaskTimerAsynchronously(WakeShow.getPlugin(),20,20);
    }

    public OpenedGui getOpenedGui(Player player){
        return openedGuiMap.get(player.getUniqueId());
    }

    public void putOpenGui(Player player, WxScreen screen){
        openedGuiMap.put(player.getUniqueId(),new OpenedGui(player,screen));
    }
}
