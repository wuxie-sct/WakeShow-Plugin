package me.wuxie.wakeshow.wakeshow.util;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.WakeShow;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class TickUtil {
    @Getter
    private static int tick = 0;
    @Getter
    private static BukkitTask tickTask;
    public static void init(){
        if(tickTask!=null&&!tickTask.isCancelled()){
            tickTask.cancel();
        }
        tickTask = Bukkit.getScheduler().runTaskTimerAsynchronously(WakeShow.getPlugin(),()->tick++,1,1);
    }
}
